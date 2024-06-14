package example.micronaut.graphql.fetcher.postsIt.mutator

import example.micronaut.entities.user.ADMIN_ROLE
import example.micronaut.exception.ForbiddenException
import example.micronaut.exception.NotFoundException
import example.micronaut.exception.NullIdException
import example.micronaut.exception.UserNotAuthenticatedException
import example.micronaut.manager.PostItManager
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import io.micronaut.security.utils.SecurityService
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class DeletePostItFetcher(
    private val postItManager: PostItManager,
    @Inject private val securityService: SecurityService,
): DataFetcher<Long> {

    override fun get(env: DataFetchingEnvironment?): Long {
        val id = env?.getArgument<String>("id") ?: throw NullIdException()

        val username = securityService.username().orElseGet { throw UserNotAuthenticatedException()}
        val postIt = postItManager.getPostIt(id).toFuture().get()  ?: throw NotFoundException(type = "PostIt",id=id)

        if (postIt.createdBy != username && !securityService.hasRole(ADMIN_ROLE)) {
            throw ForbiddenException(user=username, action="delete post-it: $id")
        }
        return postItManager.deletePostIt(id).toFuture().get()
    }

}