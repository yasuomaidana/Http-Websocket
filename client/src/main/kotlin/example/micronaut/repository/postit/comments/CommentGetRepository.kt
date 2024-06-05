package example.micronaut.repository.postit.comments

import example.micronaut.configuration.mongo.DefaultMongoRepository
import example.micronaut.entities.mongo.postit.Comment
import io.micronaut.data.repository.CrudRepository
import org.bson.types.ObjectId

@DefaultMongoRepository
interface CommentGetRepository: CrudRepository<Comment, ObjectId>