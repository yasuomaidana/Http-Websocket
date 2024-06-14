package example.micronaut.graphql.fetcher.postsIt.comments.mutator

import example.micronaut.entities.mongo.postit.Comment
import example.micronaut.entities.user.ADMIN_ROLE
import example.micronaut.exception.ForbiddenException
import example.micronaut.exception.NotFoundException
import example.micronaut.exception.UserNotAuthenticatedException
import example.micronaut.manager.PostItManager
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import io.micronaut.security.utils.SecurityService
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class UpdateCommentFetcher(
    private val postItManager: PostItManager,
    @Inject private val securityService: SecurityService,
): DataFetcher<Comment> {
    override fun get(environment: DataFetchingEnvironment): Comment {
        val id = environment.getArgument<String>("id")!!
        val title = environment.getArgument<String>("title")
        val content = environment.getArgument<String>("content")
        val comment = postItManager.getComment(id).toFuture().get() ?: throw NotFoundException(type = "Comment", id = id)

        val username = securityService.username().orElseGet { throw UserNotAuthenticatedException() }
        if (title != null) {
            if (comment.createdBy != username && !securityService.hasRole(ADMIN_ROLE)) {
                throw ForbiddenException(user = username, action = "update title of post-it: $id")
            }
        }
        return postItManager.updateComment(id, title, content).toFuture().get()
    }
}