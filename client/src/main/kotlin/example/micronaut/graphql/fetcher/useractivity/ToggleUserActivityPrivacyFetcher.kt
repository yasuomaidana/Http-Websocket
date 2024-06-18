package example.micronaut.graphql.fetcher.useractivity

import example.micronaut.exception.UserNotAuthenticatedException
import example.micronaut.graphql.dto.UserActivityDTO
import example.micronaut.graphql.mapper.UserActivityMapper
import example.micronaut.service.UserActivityService
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import io.micronaut.security.utils.SecurityService
import jakarta.inject.Singleton

@Singleton
class ToggleUserActivityPrivacyFetcher(
    private val userActivityService: UserActivityService,
    private val userActivityMapper: UserActivityMapper,
    private val securityService: SecurityService
): DataFetcher<UserActivityDTO> {
    override fun get(environment: DataFetchingEnvironment): UserActivityDTO {

        val username = environment.getArgument<String>("username") ?: securityService.username().orElseGet { throw UserNotAuthenticatedException() }

        val userActivity = userActivityService.togglePublicFlag(username).toFuture().get()
        val postsOffset = environment.getArgument<Int>("postsOffset") ?: 0
        val postsLimit = environment.getArgument<Int>("postsLimit") ?: 10
        val commentsOffset = environment.getArgument<Int>("commentsOffset") ?: 0
        val commentsLimit = environment.getArgument<Int>("commentsLimit") ?: 10


        return userActivityMapper.userActivityToUserActivityDTO(userActivity,
            postsOffset,
            postsLimit,
            commentsOffset,
            commentsLimit)
    }
}