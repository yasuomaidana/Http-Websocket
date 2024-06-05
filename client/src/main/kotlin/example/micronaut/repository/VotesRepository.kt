package example.micronaut.repository

import example.micronaut.configuration.mongo.DefaultMongoRepository
import example.micronaut.entities.mongo.postit.Votes
import io.micronaut.data.repository.reactive.ReactorCrudRepository
import org.bson.types.ObjectId

@DefaultMongoRepository
interface VotesRepository: ReactorCrudRepository<Votes, ObjectId>