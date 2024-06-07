package example.micronaut.manager

import example.micronaut.entities.mongo.postit.Comment
import example.micronaut.entities.mongo.postit.PostIt
import example.micronaut.service.postit.CommentService
import example.micronaut.service.postit.PostItService
import jakarta.inject.Singleton
import org.bson.types.ObjectId
import reactor.core.publisher.Mono
import kotlin.jvm.optionals.getOrNull

@Singleton
class PostItManager(
    private val postItService: PostItService,
    private val commentService: CommentService
) {
    fun createPostIt(postIt: PostIt): Mono<PostIt> {
        return postItService.createPostIt(postIt)
    }

    fun addCommentToPostIt(postItId: ObjectId, comment: Comment): Mono<PostIt> {
        return commentService.createComment(comment).flatMap { createdComment ->
            postItService.addCommentToPostIt(postItId, createdComment.id!!)
        }
    }

    fun removeCommentFromPostIt(postItId: ObjectId, commentId: ObjectId): Mono<PostIt> {
        return postItService.removeCommentFromPostIt(postItId, commentId)
    }

    fun getPostIt(id: ObjectId): PostIt? {
        return postItService.getPostIt(id).getOrNull()
    }

    fun getPostIt(id:String) = getPostIt(ObjectId(id))
}