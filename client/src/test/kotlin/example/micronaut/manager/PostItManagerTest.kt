package example.micronaut.manager

import example.micronaut.entities.mongo.postit.Comment
import example.micronaut.entities.mongo.postit.PostIt
import example.micronaut.repository.postit.PostItRepository
import example.micronaut.repository.postit.comments.CommentRepository
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

@MicronautTest(transactional = false)
class PostItManagerTest {

    @Inject
    lateinit var postItManager: PostItManager

    @Inject
    lateinit var postItRepository: PostItRepository

    @Inject
    lateinit var commentRepository: CommentRepository

    @AfterEach
    fun tearDown() {
        postItRepository.deleteAll().block()
        commentRepository.deleteAll().block()
    }

    @Test
    fun testCreatePostIt() {
        val postIt = PostIt(title = "Test PostIt", content = "Test content", childPostItIds = emptyList(), color = "red", commentIds = emptyList())
        val createdPostIt = postItManager.createPostIt(postIt).block()!!
        assertNotNull(createdPostIt)
        assertNotNull(createdPostIt.id)
    }

    @Test
    fun testAddCommentToPostIt() {
        val postIt = PostIt(title = "Test PostIt", content = "Test content", childPostItIds = emptyList(), color = "red", commentIds = emptyList())
        val createdPostIt = postItManager.createPostIt(postIt).block()!!
        val comment = Comment(postId = createdPostIt.id!!, title = "Test comment", content = "Test comment content")
        val updatedPostIt = postItManager.addCommentToPostIt(createdPostIt.id!!, comment).block()!!
        assertTrue(updatedPostIt.commentIds.contains(comment.id))
    }

    @Test
    fun testRemoveCommentFromPostIt() {
        val postIt = PostIt(title = "Test PostIt", content = "Test content", childPostItIds = emptyList(), color = "red", commentIds = emptyList())
        val createdPostIt = postItManager.createPostIt(postIt).block()!!
        val comment = Comment(postId = createdPostIt.id!!, title = "Test comment", content = "Test comment content")
        val updatedPostIt = postItManager.addCommentToPostIt(createdPostIt.id!!, comment).block()!!
        val updatedPostIt2 = postItManager.removeCommentFromPostIt(updatedPostIt.id!!, comment.id!!).block()!!
        assertFalse(updatedPostIt2.commentIds.contains(comment.id))
    }
}