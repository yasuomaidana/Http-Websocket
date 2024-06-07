package example.micronaut.service.postit

import example.micronaut.entities.mongo.postit.PostIt
import example.micronaut.repository.postit.PostItGetRepository
import example.micronaut.repository.postit.PostItRepository
import jakarta.inject.Singleton
import org.bson.types.ObjectId
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Service class for managing PostIt objects.
 *
 * Note: We use two separate repository interfaces, PostItRepository and PostItGetRepository,
 * because Micronaut Data MongoDB has different APIs for reactive and non-reactive operations.
 * PostItRepository is used for reactive operations (e.g., creating, updating, deleting),
 * while PostItGetRepository is used for non-reactive operations (e.g., retrieving by ID).
 */
@Singleton
class PostItService(
    private val postItRepository: PostItRepository,
    private val postItGetRepository: PostItGetRepository,
) {
    fun createPostIt(postIt: PostIt): Mono<PostIt> =
        postItRepository.save(postIt)

    fun getPostIt(id: ObjectId): Mono<PostIt> =
        Mono.from(postItRepository.find(id))

    fun deletePostIt(id: ObjectId): Mono<Long> =
        postItRepository.deleteById(id)

    fun updatePostIt(updatedPostIt: PostIt): Mono<PostIt> =
        postItRepository.update(updatedPostIt)

    fun addCommentToPostIt(postItId: ObjectId, commentId: ObjectId): Mono<PostIt> {
        return postItGetRepository.findById(postItId).let { postIt ->
            if (postIt.isPresent) {
                val updatedPostIt = postIt.get()
                updatedPostIt.commentIds += commentId
                return postItRepository.update(updatedPostIt)
            }
            Mono.empty()
        }
    }

    fun removeCommentFromPostIt(postItId: ObjectId, commentId: ObjectId): Mono<PostIt> {
        return postItGetRepository.findById(postItId).let { postIt ->
            if (postIt.isPresent) {
                val updatedPostIt = postIt.get()
                updatedPostIt.commentIds -= commentId
                return postItRepository.update(updatedPostIt)
            }
            Mono.empty()
        }
    }

    fun clearCommentsFromPostIt(postItId: ObjectId): Mono<PostIt> {
        return postItGetRepository.findById(postItId).let { postIt ->
            if (postIt.isPresent) {
                val updatedPostIt = postIt.get()
                updatedPostIt.commentIds = emptyList()
                return postItRepository.update(updatedPostIt)
            }
            Mono.empty()
        }
    }

    fun getPosts(): Flux<PostIt> {
        return postItRepository.findAll()
    }
}