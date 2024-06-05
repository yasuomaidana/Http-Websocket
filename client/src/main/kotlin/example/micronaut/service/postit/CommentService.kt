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
    private val commentGetRepository: CommentGetRepository,
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

    fun updateLikeComment(commentId: ObjectId, like: Boolean): Mono<Comment> {
        return commentGetRepository.findById(commentId).let { comment ->
            if (comment.isPresent) {
                val updatedComment = comment.get()
                updatedComment.votes.likes = maxOf(0, updatedComment.votes.likes + if (like) 1 else -1)
                return commentRepository.update(updatedComment)
            }
            Mono.empty()
        }
    }

    fun updateDisLikeComment(commentId: ObjectId, like: Boolean): Mono<Comment> {
        return commentGetRepository.findById(commentId).let { comment ->
            if (comment.isPresent) {
                val updatedComment = comment.get()
                updatedComment.votes.dislikes = maxOf(0, updatedComment.votes.dislikes + if (like) 1 else -1)
                return commentRepository.update(updatedComment)
            }
            Mono.empty()
        }
    }
}