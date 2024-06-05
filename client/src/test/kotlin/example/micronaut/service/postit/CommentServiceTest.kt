package example.micronaut.service.postit

import example.micronaut.entities.mongo.postit.Comment
import example.micronaut.entities.mongo.postit.Votes
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

@MicronautTest(transactional = false)
class CommentServiceTest {

    @Inject
    lateinit var commentService: CommentService

    @Test
    fun createComment() {
        val votes = Votes(likes = 10, dislikes = 2)
        val comment = Comment(title = "Test comment", description = "Test description", votes = votes)
        val result = commentService.createComment(comment).block()
        assertNotNull(result)
        assertEquals(comment.title, result?.title)
        assertEquals(comment.description, result?.description)
        assertEquals(comment.votes.likes, result?.votes?.likes)
        assertEquals(comment.votes.dislikes, result?.votes?.dislikes)
    }
}