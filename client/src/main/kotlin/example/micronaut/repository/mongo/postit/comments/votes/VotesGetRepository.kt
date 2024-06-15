package example.micronaut.repository.mongo.postit.comments.votes

import example.micronaut.configuration.mongo.DefaultMongoRepository
import example.micronaut.entities.mongo.postit.Votes
import io.micronaut.data.repository.CrudRepository
import org.bson.types.ObjectId

//@DefaultMongoRepository
interface VotesGetRepository: CrudRepository<Votes, ObjectId>