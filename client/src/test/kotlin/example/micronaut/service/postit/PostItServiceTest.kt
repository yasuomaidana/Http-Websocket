package example.micronaut.service.postit

import example.micronaut.entities.mongo.postit.PostIt
import example.micronaut.repository.postit.PostItRepository
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

@MicronautTest(transactional = false)
class PostItServiceTest {

    @Inject
    lateinit var postItRepository: PostItRepository
    @Inject
    lateinit var postItService: PostItService

    @AfterEach
    fun tearDown() {
        postItRepository.deleteAll().block()
    }

    @Test
    fun testCreatePostIt() {
        val postIt = PostIt(title = "Test PostIt", content = "Test content", childPostItIds = emptyList(), color = "red", commentIds = emptyList())
        val createdPostIt = postItService.createPostIt(postIt).block()!!
        assertNotNull(createdPostIt)
        assertNotNull(createdPostIt.id)
    }

    @Test
    fun testGetPostIt() {
        val postIt = PostIt(title = "Test PostIt", content = "Test content", childPostItIds = emptyList(), color = "red", commentIds = emptyList())
        val createdPostIt = postItService.createPostIt(postIt).block()!!
        val retrievedPostIt = postItService.getPostIt(createdPostIt.id!!)
        assertTrue(retrievedPostIt.isPresent)
    }

    @Test
    fun testDeletePostIt() {
        val postIt = PostIt(title = "Test PostIt", content = "Test content", childPostItIds = emptyList(), color = "red", commentIds = emptyList())
        val createdPostIt = postItService.createPostIt(postIt).block()!!
        val deletedCount = postItService.deletePostIt(createdPostIt.id!!).block()!!
        assertEquals(1, deletedCount)
    }

    @Test
    fun testUpdatePostIt() {
        val postIt = PostIt(title = "Test PostIt", content = "Test content", childPostItIds = emptyList(), color = "red", commentIds = emptyList())
        val createdPostIt = postItService.createPostIt(postIt).block()!!
        createdPostIt.title = "Updated title"
        val updatedPostIt = postItService.updatePostIt(createdPostIt).block()!!
        assertEquals("Updated title", updatedPostIt.title)
    }
}