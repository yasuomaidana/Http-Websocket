package example.micronaut.repository.postit.comments

import example.micronaut.configuration.mongo.DefaultMongoRepository
import example.micronaut.entities.mongo.postit.Comment
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.data.repository.reactive.ReactorCrudRepository
import org.bson.types.ObjectId
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono

@DefaultMongoRepository
interface CommentRepository: ReactorCrudRepository<Comment, ObjectId>{
    fun find(id: ObjectId): Publisher<Comment> = findById(id)
    fun findByIdIn(ids: List<ObjectId>, pageable: Pageable): Mono<Page<Comment>>
}