package example.micronaut.service.postit

import example.micronaut.entities.mongo.postit.Comment
import example.micronaut.entities.mongo.postit.Votes
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

@MicronautTest(transactional = false)
class CommentServiceTest {

    @Inject
    lateinit var commentService: CommentService

    @Test
    fun createComment() {
        val votes = Votes(likes = 10, dislikes = 2)
        val comment = Comment(postId = ObjectId(), title = "Test comment", content = "Test description", votes = votes)
        val result = commentService.createComment(comment).block()!!

        assertNotNull(result)
        assertEquals(comment.postId, result.postId)
        assertEquals(comment.title, result.title)
        assertEquals(comment.content, result.content)
        assertEquals(comment.votes.likes, result.votes.likes)
        assertEquals(comment.votes.dislikes, result.votes.dislikes)
    }

    @Test
    fun getComment() {
        val votes = Votes(likes = 10, dislikes = 2)
        val comment = Comment(postId = ObjectId(), title = "Title", content = "Test comment", votes = votes)
        val createdComment = commentService.createComment(comment).block()
        val result = commentService.getComment(createdComment!!.id!!)
        assertTrue(result.isPresent)
        assertEquals(createdComment.id, result.get().id)
        assertEquals(createdComment.postId, result.get().postId)
        assertEquals(createdComment.content, result.get().content)
        assertEquals(createdComment.votes.likes, result.get().votes.likes)
        assertEquals(createdComment.votes.dislikes, result.get().votes.dislikes)
    }

    @Test
    fun deleteComment() {
        val votes = Votes(likes = 10, dislikes = 2)
        val comment = Comment(postId = ObjectId(), title = "Title", content = "Test comment", votes = votes)
        val createdComment = commentService.createComment(comment).block()
        val deletedCount = commentService.deleteComment(createdComment!!.id!!).block()!!
        assertEquals(1, deletedCount)
        val result = commentService.getComment(createdComment.id!!)
        assertFalse(result.isPresent)
    }

    @Test
    fun updateComment() {
        val votes = Votes(likes = 10, dislikes = 2)
        val comment = Comment(postId = ObjectId(), title = "Test comment", votes = votes)
        val createdComment = commentService.createComment(comment).block()
        val updatedComment = createdComment!!.copy(title = "Updated comment")
        val result = commentService.updateComment(updatedComment).block()!!
        assertNotNull(result)
        assertEquals(updatedComment.id, result.id)
        assertEquals(updatedComment.postId, result.postId)
        assertEquals(updatedComment.title, result.title)
        assertEquals(updatedComment.votes.likes, result.votes.likes)
        assertEquals(updatedComment.votes.dislikes, result.votes.dislikes)
    }
}