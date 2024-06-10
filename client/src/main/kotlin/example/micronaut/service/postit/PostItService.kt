package example.micronaut.service.postit

import example.micronaut.entities.mongo.postit.PostIt
import example.micronaut.repository.postit.PostItRepository
import jakarta.inject.Singleton
import org.bson.types.ObjectId
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Singleton
class PostItService(
    private val postItRepository: PostItRepository
) {
    fun createPostIt(postIt: PostIt): Mono<PostIt> =
        postItRepository.save(postIt)

    fun getPostIt(id: ObjectId): Mono<PostIt> =
        Mono.from(postItRepository.find(id))

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

}