package example.micronaut.manager

import example.micronaut.entities.mongo.postit.Comment
import example.micronaut.entities.mongo.postit.PostIt
import example.micronaut.service.postit.CommentService
import example.micronaut.service.postit.PostItService
import jakarta.inject.Singleton
import org.bson.types.ObjectId
import reactor.core.publisher.Mono

@Singleton
class PostItManager(
    private val postItService: PostItService,
    private val commentService: CommentService
) {
    fun createPostIt(postIt: PostIt): Mono<PostIt> {
        return postItService.createPostIt(postIt)
    }

    fun addCommentToPostIt(postItId: String, comment: Comment): Mono<PostIt> {
        return addCommentToPostIt(ObjectId(postItId), comment)
    }

    fun addCommentToPostIt(postItId: ObjectId, comment: Comment): Mono<PostIt> {
        return commentService.createComment(comment).flatMap { createdComment ->
            postItService.addCommentToPostIt(postItId, createdComment.id!!)
        }
    }

    fun removeCommentFromPostIt(postItId: ObjectId, commentId: ObjectId): Mono<PostIt> {
        return postItService.removeCommentFromPostIt(postItId, commentId)
    }

    fun getPostIt(id: ObjectId) = postItService.getPostIt(id)


    fun getPostIt(id:String) = getPostIt(ObjectId(id))

    fun getPosts(offset: Int, limit: Int) = postItService.getPosts(offset, limit)

    fun getComment(id: String) = commentService.getComment(id)
    fun getComment(id: ObjectId) = commentService.getComment(id)

    fun createChildPostIt(parentId: ObjectId, childPostIt: PostIt) = postItService.createChildPostIt(parentId, childPostIt)
    fun createChildPostIt(postItId: String, childPostIt: PostIt) = createChildPostIt(ObjectId(postItId), childPostIt)

    fun addChildPostIt(postItId: ObjectId, childPostItId: ObjectId) = postItService.addChildPostIt(postItId, childPostItId)
    fun addChildPostIt(postItId: String, childPostItId: String) = addChildPostIt(ObjectId(postItId), ObjectId(childPostItId))

    fun removeChildPostIt(postItId: ObjectId, childPostItId: ObjectId) = postItService.removeChildPostIt(postItId, childPostItId)
    fun removeChildPostIt(postItId: String, childPostItId: String) = removeChildPostIt(ObjectId(postItId), ObjectId(childPostItId))

    fun changeParentPostIt(parentId:ObjectId, childPostItId: ObjectId, newParentPostItId: ObjectId) = postItService.changeParentPostIt(parentId,childPostItId, newParentPostItId)
    fun changeParentPostIt(parentId:String, childPostItId: String, newParentPostItId: String) = changeParentPostIt(ObjectId(parentId), ObjectId(childPostItId), ObjectId(newParentPostItId))

    fun getPostsByIds(ids: List<ObjectId>, offset: Int, limit: Int) = postItService.getByIds(ids, offset, limit)

    fun getComments(ids: List<ObjectId>, offset: Int, limit: Int) = commentService.getComments(ids, offset, limit)

    fun getCommentFromPostIt(postItId: ObjectId, offset: Int, limit: Int) = getPostIt(postItId).flatMap { postIt ->
        getComments(postIt.commentIds, offset, limit)
    }
    fun getCommentFromPostIt(postItId: String, offset: Int, limit: Int) = getCommentFromPostIt(ObjectId(postItId), offset, limit)
}