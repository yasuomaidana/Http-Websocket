package example.micronaut.service.postit

import example.micronaut.entities.mongo.postit.PostIt
import example.micronaut.exception.NotFoundException
import example.micronaut.repository.mongo.postit.PostItRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import org.bson.types.ObjectId
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Singleton
class PostItService(
    private val postItRepository: PostItRepository,
) {
    fun createPostIt(postIt: PostIt): Mono<PostIt> =
        postItRepository.save(postIt)

    fun getPostIt(id: ObjectId): Mono<PostIt> =
        assertExists(id).flatMap { Mono.from(postItRepository.find(id)) }

    fun deletePostIt(id: ObjectId): Mono<Long> =
        postItRepository.deleteById(id)

    fun updatePostIt(updatedPostIt: PostIt): Mono<PostIt> =
        postItRepository.update(updatedPostIt)

    fun addCommentToPostIt(postItId: ObjectId, commentId: ObjectId): Mono<PostIt> =
        getPostIt(postItId).flatMap { postIt ->
            postIt.commentIds += commentId
            postItRepository.update(postIt)
        }

    fun removeCommentFromPostIt(postItId: ObjectId, commentId: ObjectId): Mono<PostIt> =
        getPostIt(postItId).flatMap { postIt ->
            postIt.commentIds -= commentId
            postItRepository.update(postIt)
        }


    fun clearCommentsFromPostIt(postItId: ObjectId): Mono<PostIt> =
        getPostIt(postItId).flatMap { postIt ->
            postIt.commentIds = emptyList()
            postItRepository.update(postIt)
        }

    fun getPosts(): Flux<PostIt> = postItRepository.findAll()

    fun getByIds(ids: List<ObjectId>, offset: Int = 0, limit: Int = 10): Mono<Page<PostIt>> =
        postItRepository.findByIdIn(ids, Pageable.from(offset, limit))

    fun getPosts(offset: Int, limit: Int): Mono<Page<PostIt>>? =
        postItRepository.findAll(Pageable.from(offset, limit))

    fun addChildPostIt(postItId: ObjectId, childPostItId: ObjectId): Mono<PostIt> =
        getPostIt(postItId).flatMap { postIt ->
            checkExists(childPostItId).flatMap {
                postIt.childPostItIds += childPostItId
                postItRepository.update(postIt)
            }
        }

    fun createChildPostIt(postItId: ObjectId, childPostIt: PostIt): Mono<PostIt> =
        checkExists(postItId).flatMap {
            createPostIt(childPostIt).flatMap { createdChildPostIt ->
                addChildPostIt(postItId, createdChildPostIt.id!!)
            }
        }

    fun removeChildPostIt(postItId: ObjectId, childPostItId: ObjectId): Mono<PostIt> =
        getPostIt(postItId).flatMap { postIt ->
            checkExists(childPostItId).flatMap {
                postIt.childPostItIds -= childPostItId
                postItRepository.update(postIt)
            }
        }

    fun changeParentPostIt(parentId: ObjectId, childId: ObjectId, newParentId: ObjectId): Mono<PostIt> =
        checkExists(parentId).zipWith(checkExists(childId)).zipWith(checkExists(newParentId)).then(
            removeChildPostIt(parentId, childId).then(addChildPostIt(newParentId, childId))
        )

    fun checkExists(id: ObjectId): Mono<Boolean> =
        postItRepository.existsById(id)

    fun assertExists(id: ObjectId): Mono<Boolean> =
        checkExists(id).flatMap {
            if (!it) {
                throw NotFoundException("PostIt", id.toString())
            }
            Mono.just(it)
        }
}