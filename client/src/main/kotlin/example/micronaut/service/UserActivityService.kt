package example.micronaut.service

import example.micronaut.entities.mongo.postit.useractivity.UserActivity
import example.micronaut.repository.mongo.UserActivityRepository
import jakarta.inject.Singleton
import reactor.core.publisher.Mono

@Singleton
class UserActivityService(
    private val userActivityRepository: UserActivityRepository
) {
    fun getUserActivity(username: String) =
        userActivityRepository.findByUsername(username)
            .switchIfEmpty(Mono.defer {
                userActivityRepository.save(UserActivity(username))
            })

    fun updateUserActivity(userActivity: UserActivity) =
        getUserActivity(userActivity.username).flatMap { userActivityRepository.update(userActivity) }
    fun deleteUserActivity(userActivity: UserActivity) =
        userActivityRepository.delete(userActivity)
}