package example.micronaut.graphql.fetcher.postsIt.mutator

import example.micronaut.entities.user.ADMIN_ROLE
import example.micronaut.exception.ForbiddenException
import example.micronaut.exception.NotFoundException
import example.micronaut.exception.UserNotAuthenticatedException
import example.micronaut.graphql.dto.PostItDTO
import example.micronaut.graphql.mapper.PostItMapper
import example.micronaut.manager.PostItManager
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import io.micronaut.security.utils.SecurityService
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class UpdatePostItFetcher(
    private val postItManager: PostItManager,
    private val postItMapper: PostItMapper,
    @Inject private val securityService: SecurityService,
): DataFetcher<PostItDTO> {
    override fun get(environment: DataFetchingEnvironment): PostItDTO {
        val id = environment.getArgument<String>("id")!!
        val title = environment.getArgument<String>("title")
        val content = environment.getArgument<String>("content")
        val color = environment.getArgument<String>("color")

        val postIt = postItManager.getPostIt(id).toFuture().get() ?: throw NotFoundException(type = "PostIt", id = id)

        if (title != null) {
            val username = securityService.username().orElseGet { throw UserNotAuthenticatedException() }
            if (postIt.createdBy != username && !securityService.hasRole(ADMIN_ROLE)) {
                throw ForbiddenException(user = username, action = "update title of post-it: $id")
            }
        }

        return postItManager.updatePostIt(id, title, content, color).toFuture().get().let { postItMapper.postItToPostItDTO(it) }
    }
}