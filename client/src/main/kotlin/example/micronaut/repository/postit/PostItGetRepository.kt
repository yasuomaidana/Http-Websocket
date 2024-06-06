package example.micronaut.repository.postit

import example.micronaut.configuration.mongo.DefaultMongoRepository
import example.micronaut.entities.mongo.postit.PostIt
import io.micronaut.data.repository.CrudRepository
import org.bson.types.ObjectId

@DefaultMongoRepository
interface PostItGetRepository: CrudRepository<PostIt, ObjectId>