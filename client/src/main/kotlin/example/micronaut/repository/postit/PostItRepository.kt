package example.micronaut.repository.postit

import example.micronaut.configuration.mongo.DefaultMongoRepository
import example.micronaut.entities.mongo.postit.PostIt
import io.micronaut.data.repository.reactive.ReactorCrudRepository
import org.bson.types.ObjectId
import org.reactivestreams.Publisher

@DefaultMongoRepository
interface PostItRepository: ReactorCrudRepository<PostIt, ObjectId>{
    fun find(id: ObjectId): Publisher<PostIt> = findById(id)
}