package example.micronaut.repository.mongo

import example.micronaut.configuration.mongo.DefaultMongoRepository
import example.micronaut.entities.mongo.postit.useractivity.UserActivity
import io.micronaut.data.repository.reactive.ReactorCrudRepository
import org.bson.types.ObjectId
import reactor.core.publisher.Mono

@DefaultMongoRepository
interface UserActivityRepository: ReactorCrudRepository<UserActivity, ObjectId> {
    fun findByUsername(username: String): Mono<UserActivity>
}