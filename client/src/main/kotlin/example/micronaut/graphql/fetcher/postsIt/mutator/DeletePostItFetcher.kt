package example.micronaut.graphql.fetcher.postsIt.mutator

import example.micronaut.entities.user.ADMIN_ROLE
import example.micronaut.exception.ForbiddenException
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
): DataFetcher<Boolean> {

    override fun get(env: DataFetchingEnvironment?): Boolean {
        val id = env?.getArgument<String>("id") ?: throw IllegalArgumentException("ID cannot be null")

        val username = securityService.username().orElseGet { throw IllegalArgumentException("User not authenticated") }
        val postIt = postItManager.getPostIt(id).toFuture().get()  ?: throw IllegalArgumentException("Post-it not found")

        if (postIt.createdBy != username && !securityService.hasRole(ADMIN_ROLE)) {
            throw ForbiddenException(user=username, action="delete post-it: $id")
        }
        return postItManager.deletePostIt(id).toFuture().isDone
    }

}