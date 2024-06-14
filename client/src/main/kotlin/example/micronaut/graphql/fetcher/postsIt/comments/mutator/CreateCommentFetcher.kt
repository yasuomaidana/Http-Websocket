package example.micronaut.graphql.fetcher.postsIt.comments.mutator

import example.micronaut.entities.mongo.postit.Comment
import example.micronaut.exception.UserNotAuthenticatedException
import example.micronaut.manager.PostItManager
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import io.micronaut.security.utils.SecurityService
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class CreateCommentFetcher(
    private val postItManager: PostItManager,
    @Inject
    private val securityService: SecurityService
) : DataFetcher<Comment> {
    override fun get(env: DataFetchingEnvironment): Comment {
        val postId = env.getArgument<String>("postId")!!
        val title = env.getArgument<String>("title")!!
        val content = env.getArgument<String>("content")
        val username = securityService.username()
            .orElseThrow {UserNotAuthenticatedException()}

        val comment = Comment(postId, title, content, username)
        return postItManager.addCommentToPostIt(postId, comment).thenReturn(comment).toFuture().get()
    }
}