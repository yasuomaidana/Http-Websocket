package example.micronaut.service

import example.micronaut.entities.mongo.Fruit
import example.micronaut.repository.FruitRepository

import jakarta.inject.Singleton
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Singleton
class FruitService(
    private val fruitRepository: FruitRepository
) {

    fun createFruit(fruit: Fruit): Mono<Fruit> {
        return fruitRepository.save(fruit)
    }

    fun getFruit(id: String): Mono<Fruit> {
        return fruitRepository.findById(id)
    }

    fun updateFruit(fruit: Fruit): Mono<Fruit> {
        return fruitRepository.save(fruit)
    }

    fun deleteFruit(id: String): Mono<Long> {
        return fruitRepository.deleteById(id)
    }

    fun getFruitsByName(name: String): Flux<Fruit> {
        return fruitRepository.findByName(name)
    }

    fun getFruitsByWeightGreaterThan(weight: Double): Flux<Fruit> {
        return fruitRepository.findByWeightGreaterThan(weight)
    }

    fun deleteAll(): Mono<Long> {
        return fruitRepository.deleteAll()
    }
}