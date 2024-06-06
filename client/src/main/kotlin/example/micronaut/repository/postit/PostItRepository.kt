package example.micronaut.repository.postit

import example.micronaut.configuration.mongo.DefaultMongoRepository
import example.micronaut.entities.mongo.postit.PostIt
import io.micronaut.data.repository.reactive.ReactorCrudRepository
import org.bson.types.ObjectId

@DefaultMongoRepository
interface PostItRepository: ReactorCrudRepository<PostIt, ObjectId>