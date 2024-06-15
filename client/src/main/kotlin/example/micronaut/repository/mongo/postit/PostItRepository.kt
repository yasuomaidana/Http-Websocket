package example.micronaut.repository.mongo.postit

import example.micronaut.configuration.mongo.DefaultMongoRepository
import example.micronaut.entities.mongo.postit.PostIt
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.data.repository.reactive.ReactorPageableRepository
import org.bson.types.ObjectId
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono

@DefaultMongoRepository
interface PostItRepository: ReactorPageableRepository<PostIt, ObjectId>{
    fun find(id: ObjectId): Publisher<PostIt> = findById(id)
    fun findByIdIn(ids: List<ObjectId>, pageable: Pageable): Mono<Page<PostIt>>
}