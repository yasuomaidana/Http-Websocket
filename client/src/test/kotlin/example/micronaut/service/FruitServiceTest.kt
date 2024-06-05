package example.micronaut.service

import example.micronaut.entities.mongo.Fruit
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@MicronautTest(transactional = false)
class FruitServiceTest {

    @Inject
    private lateinit var fruitService: FruitService

    @BeforeEach
    fun setup() {
        // Clear the database before each test
        fruitService.deleteAll().block()
    }

    @AfterEach
    fun cleanup() {
        // Clear the database after each test
        fruitService.deleteAll().block()
    }

    @Test
    fun testCreateFruit() {
        val fruit = Fruit(name="Apple", weight =  1.0)
        val createdFruit = fruitService.createFruit(fruit).block()
        Assertions.assertEquals(fruit.id, createdFruit!!.id)
        Assertions.assertEquals(fruit.name, createdFruit.name)
        Assertions.assertEquals(fruit.weight, createdFruit.weight)
    }

    @Test
    fun testGetFruit() {
        val fruit = Fruit(name="Apple", weight =  1.0)
        val createdFruit = fruitService.createFruit(fruit).block()!!
        val retrievedFruit = fruitService.getFruit(createdFruit.id!!).block()
        Assertions.assertEquals(fruit.id, retrievedFruit!!.id)
        Assertions.assertEquals(fruit.name, retrievedFruit.name)
        Assertions.assertEquals(fruit.weight, retrievedFruit.weight)
    }

    @Test
    fun testUpdateFruit() {
        val fruit = Fruit(name="Apple", weight =  1.0)
        val createdFruit = fruitService.createFruit(fruit).block()!!
        createdFruit.weight = 2.0
        val updatedFruit = fruitService.updateFruit(createdFruit).block()
        Assertions.assertEquals(createdFruit.id, updatedFruit!!.id)
        Assertions.assertEquals(createdFruit.name, updatedFruit.name)
        Assertions.assertEquals(createdFruit.weight, updatedFruit.weight)
    }

    @Test
    fun testDeleteFruit() {
        val fruit = Fruit(name="Apple", weight =  1.0)
        val createdFruit = fruitService.createFruit(fruit).block()!!
        fruitService.deleteFruit(createdFruit.id!!).block()
        val retrievedFruit = fruitService.getFruit(createdFruit.id!!).block()
        Assertions.assertNull(retrievedFruit)
    }

    @Test
    fun testGetFruitsByName() {
        val fruit1 = Fruit(name="Apple", weight =  1.0)
        val fruit2 = Fruit(name="Apple", weight =  2.0)
        fruitService.createFruit(fruit1).block()
        fruitService.createFruit(fruit2).block()
        val retrievedFruits = fruitService.getFruitsByName("Apple").collectList().block()
        Assertions.assertEquals(2, retrievedFruits!!.size)
        Assertions.assertEquals(fruit1.name, retrievedFruits[0].name)
        Assertions.assertEquals(fruit1.weight, retrievedFruits[0].weight)
        Assertions.assertEquals(fruit2.name, retrievedFruits[1].name)
        Assertions.assertEquals(fruit2.weight, retrievedFruits[1].weight)
    }

    @Test
    fun testGetFruitsByWeightGreaterThan() {
        val fruit1 = Fruit(name="Apple", weight =  1.0)
        val fruit2 = Fruit(name="Banana", weight =  2.0)
        fruitService.createFruit(fruit1).block()
        fruitService.createFruit(fruit2).block()
        val retrievedFruits = fruitService.getFruitsByWeightGreaterThan(1.5).collectList().block()
        Assertions.assertEquals(1, retrievedFruits!!.size)
        Assertions.assertEquals(fruit2.name, retrievedFruits[0].name)
        Assertions.assertEquals(fruit2.weight, retrievedFruits[0].weight)
    }
//    @Test
//    fun testGetFruitsByWeightGreaterThan() {
//        val fruit1 = Fruit(name="Apple", weight =  1.0)
//        val fruit2 = Fruit(name="Apple", weight =  2.0)
//        val fruit3 = Fruit(name="Banana", weight =  3.0)
//        fruitService.createFruit(fruit1)
//        fruitService.createFruit(fruit2)
//        fruitService.createFruit(fruit3)
//        val fruits = fruitService.getFruitsByWeightGreaterThan(1.5).collectList().block()!!
//        Assertions.assertEquals(2, fruits.size)
//        Assertions.assertTrue(fruits.any { it.id == fruit2.id })
//        Assertions.assertTrue(fruits.any { it.id == fruit3.id })
//    }
//
//    @Test
//    fun deleteFruit() {
//    }
}