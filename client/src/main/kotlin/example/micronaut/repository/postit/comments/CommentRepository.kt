package example.micronaut.repository.postit.comments

import example.micronaut.configuration.mongo.DefaultMongoRepository
import example.micronaut.entities.mongo.postit.Comment
import io.micronaut.data.repository.reactive.ReactorCrudRepository
import org.bson.types.ObjectId

@DefaultMongoRepository
interface CommentRepository: ReactorCrudRepository<Comment, ObjectId>