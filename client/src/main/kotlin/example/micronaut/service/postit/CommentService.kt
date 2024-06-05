package example.micronaut.service.postit

import example.micronaut.entities.mongo.postit.Comment
import example.micronaut.repository.postit.comments.CommentGetRepository
import example.micronaut.repository.postit.comments.CommentRepository
import jakarta.inject.Singleton
import org.bson.types.ObjectId
import reactor.core.publisher.Mono
import java.util.*

@Singleton
class CommentService(
    private val commentRepository: CommentRepository,
    private val commentGetRepository: CommentGetRepository
) {
    fun createComment(comment: Comment): Mono<Comment> =
        commentRepository.save(comment)

    fun getComment(id: ObjectId): Optional<Comment> =
        commentGetRepository.findById(id)

    fun deleteComment(id: ObjectId): Mono<Long> =
        commentRepository.deleteById(id)

    fun updateComment(updatedComment: Comment):
            Mono<Comment> =
        commentRepository.update(updatedComment)
}