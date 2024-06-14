package example.micronaut.graphql.fetcher.postsIt.comments.mutator

import example.micronaut.entities.user.ADMIN_ROLE
import example.micronaut.exception.ForbiddenException
import example.micronaut.exception.NotFoundException
import example.micronaut.exception.UserNotAuthenticatedException
import example.micronaut.manager.PostItManager
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import io.micronaut.security.utils.SecurityService
import jakarta.inject.Singleton

@Singleton
class DeleteCommentFetcher(
    private val postItManager: PostItManager,
    private val securityService: SecurityService,
): DataFetcher<Long> {
    override fun get(environment: DataFetchingEnvironment): Long {
        val id = environment.getArgument<String>("id")!!
        val username = securityService.username().orElseGet { throw UserNotAuthenticatedException() }
        val comment = postItManager.getComment(id).toFuture().get() ?: throw NotFoundException(type = "Comment", id = id)
        if (comment.createdBy != username && !securityService.hasRole(ADMIN_ROLE)) {
            throw ForbiddenException(user = username, action = "delete comment: $id")
        }
        return postItManager.deleteComment(id).toFuture().get()
    }
}