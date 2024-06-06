package example.micronaut.service.postit

import example.micronaut.entities.mongo.postit.PostIt
import example.micronaut.repository.postit.PostItGetRepository
import example.micronaut.repository.postit.PostItRepository
import jakarta.inject.Singleton
import org.bson.types.ObjectId
import reactor.core.publisher.Mono
import java.util.*

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

    fun getPostIt(id: ObjectId): Optional<PostIt> =
        postItGetRepository.findById(id)

    fun deletePostIt(id: ObjectId): Mono<Long> =
        postItRepository.deleteById(id)

    fun updatePostIt(updatedPostIt: PostIt): Mono<PostIt> =
        postItRepository.update(updatedPostIt)
}