package example.micronaut.service.postit

import example.micronaut.entities.mongo.postit.Comment
import example.micronaut.exception.NotFoundException
import example.micronaut.repository.mongo.postit.comments.CommentRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import org.bson.types.ObjectId
import reactor.core.publisher.Mono

@Singleton
class CommentService(
    private val commentRepository: CommentRepository,
) {
    fun createComment(comment: Comment): Mono<Comment> =
        commentRepository.save(comment)

    fun getComment(id: ObjectId): Mono<Comment> =
        assertExits(id).flatMap { Mono.from(commentRepository.find(id))}

    fun getComment(id:String): Mono<Comment> = getComment(ObjectId(id))

    fun deleteComment(id: ObjectId): Mono<Long> =
        commentRepository.deleteById(id)

    fun updateComment(updatedComment: Comment):
            Mono<Comment> =
        commentRepository.update(updatedComment)

    fun updateLikeComment(commentId: ObjectId, like: Boolean): Mono<Comment> {
        return getComment(commentId).flatMap { comment ->
            comment.votes.likes = maxOf(0, comment.votes.likes + if (like) 1 else -1)
            updateComment(comment)
        }
    }

    fun updateDisLikeComment(commentId: ObjectId, like: Boolean): Mono<Comment> {
        return getComment(commentId).flatMap { comment ->
            comment.votes.dislikes = maxOf(0, comment.votes.dislikes + if (like) 1 else -1)
            updateComment(comment)
        }
    }

    fun getComments(ids: List<ObjectId>, offset:Int, limit:Int): Mono<Page<Comment>> =
        commentRepository.findByIdIn(ids, Pageable.from(offset, limit))

    fun checkExists(id: ObjectId): Mono<Boolean> =
        Mono.from(commentRepository.existsById(id))

    fun assertExits(id:ObjectId) = checkExists(id).flatMap {
        if (!it) {
            throw NotFoundException("Comment", id.toString())
        }
        Mono.just(it)
    }
}