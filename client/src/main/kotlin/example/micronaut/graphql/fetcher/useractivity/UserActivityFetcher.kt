package example.micronaut.graphql.fetcher.useractivity

import example.micronaut.entities.user.ADMIN_ROLE
import example.micronaut.exception.ForbiddenException
import example.micronaut.exception.UserNotAuthenticatedException
import example.micronaut.graphql.dto.UserActivityDTO
import example.micronaut.graphql.mapper.UserActivityMapper
import example.micronaut.service.UserActivityService
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import io.micronaut.security.utils.SecurityService
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class UserActivityFetcher(
    private val userActivityService: UserActivityService,
    private val userActivityMapper: UserActivityMapper,
    @Inject
    private val securityService: SecurityService
):DataFetcher<UserActivityDTO> {
    override fun get(environment: DataFetchingEnvironment): UserActivityDTO {
        val username = environment.getArgument<String>("username") ?: securityService.username().orElseGet { throw UserNotAuthenticatedException() }

        val postsOffset = environment.getArgument<Int>("postsOffset") ?: 0
        val postsLimit = environment.getArgument<Int>("postsLimit") ?: 10
        val commentsOffset = environment.getArgument<Int>("commentsOffset") ?: 0
        val commentsLimit = environment.getArgument<Int>("commentsLimit") ?: 10
        val userActivity = userActivityService.getUserActivity(username).toFuture().get()

        if (userActivity.username != securityService.username().get() && !securityService.hasRole(ADMIN_ROLE)) {
            throw ForbiddenException(user=username, action="view user activity: $username")
        }

        return userActivityMapper.userActivityToUserActivityDTO(userActivity, postsOffset, postsLimit, commentsOffset, commentsLimit)
    }
}