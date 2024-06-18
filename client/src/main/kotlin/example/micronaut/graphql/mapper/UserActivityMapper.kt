package example.micronaut.graphql.mapper

import example.micronaut.entities.mongo.postit.useractivity.UserActivity
import example.micronaut.graphql.dto.CommentPageDTO
import example.micronaut.graphql.dto.PostItPageDTO
import example.micronaut.graphql.dto.UserActivityDTO
import example.micronaut.manager.PostItManager
import jakarta.inject.Inject
import org.bson.types.ObjectId
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper
abstract class UserActivityMapper {
    @Inject
    lateinit var postItManager: PostItManager

    @Inject
    lateinit var postItMapper: PostItMapper

    @Inject
    lateinit var commentMapper: CommentMapper

    @Mappings(
        Mapping(source = "userActivity.username", target = "username"),
        Mapping(source = "userActivity.commentVotes", target = "commentVotes"),
        Mapping(source = "userActivity.publicFlag", target = "public"),
        Mapping(target = "postIts", expression = "java(postIdsToPostItPage(userActivity.getPostItIds(), postsOffset, postsLimit))"),
        Mapping(target = "comments", expression = "java(commentIdsToCommentsPage(userActivity.getCommentIds(), commentsOffset, commentsLimit))")
    )
    abstract fun userActivityToUserActivityDTO(userActivity: UserActivity, postsOffset: Int, postsLimit: Int, commentsOffset: Int, commentsLimit: Int): UserActivityDTO

    fun postIdsToPostItPage(postIds: List<ObjectId>, offset: Int, limit: Int): PostItPageDTO {
        val postsPage = postItManager.getPostsByIds(postIds, offset, limit).toFuture().get()
        return postItMapper.postItPageToDTO(postsPage, limit)
    }

    fun commentIdsToCommentsPage(commentIds: List<ObjectId>, offset: Int, limit: Int): CommentPageDTO {
        val commentsPage = postItManager.getComments(commentIds, offset, limit).toFuture().get()
        return commentMapper.commentPageToCommentPageDTO(commentsPage)
    }
}