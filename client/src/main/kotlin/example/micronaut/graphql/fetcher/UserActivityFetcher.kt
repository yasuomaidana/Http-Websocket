package example.micronaut.graphql.fetcher

import example.micronaut.graphql.dto.UserActivityDTO
import example.micronaut.graphql.mapper.CommentMapper
import example.micronaut.graphql.mapper.PostItMapper
import example.micronaut.manager.PostItManager
import example.micronaut.service.UserActivityService
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import jakarta.inject.Singleton

@Singleton
class UserActivityFetcher(
    private val userActivityService: UserActivityService,
    private val postItManager: PostItManager,
    private val postItMapper: PostItMapper,
    private val commentMapper: CommentMapper
):DataFetcher<UserActivityDTO> {
    override fun get(environment: DataFetchingEnvironment?): UserActivityDTO {
        val username = environment?.getArgument<String>("username")!!
        val postsOffset = environment.getArgument<Int>("postsOffset") ?: 0
        val postsLimit = environment.getArgument<Int>("postsLimit") ?: 10
        val commentsOffset = environment.getArgument<Int>("commentsOffset") ?: 0
        val commentsLimit = environment.getArgument<Int>("commentsLimit") ?: 10
        val userActivity = userActivityService.getUserActivity(username).toFuture().get()

        val postsPage = postItManager.getPostsByIds(userActivity.postItIds, postsOffset, postsLimit).toFuture().get()
        val postIts = postItMapper.postItPageToDTO(postsPage, postsLimit)
        val comments = commentMapper.commentPageToCommentPageDTO(postItManager.getComments(userActivity.commentIds, commentsOffset, commentsLimit).toFuture().get())

        return UserActivityDTO(
            userActivity.username,
            postIts,
            comments,
            userActivity.commentVotes,
            userActivity.publicFlag
        )
    }
}