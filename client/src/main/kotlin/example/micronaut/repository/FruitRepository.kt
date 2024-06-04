package example.micronaut.repository

import example.micronaut.configuration.mongo.DefaultMongoRepository
import example.micronaut.entities.mongo.Fruit
import io.micronaut.data.repository.reactive.ReactorCrudRepository
import reactor.core.publisher.Flux

@DefaultMongoRepository
interface FruitRepository: ReactorCrudRepository<Fruit, String> {
    fun findByName(name: String): Flux<Fruit>
    fun findByWeightGreaterThan(weight: Double): Flux<Fruit>
}