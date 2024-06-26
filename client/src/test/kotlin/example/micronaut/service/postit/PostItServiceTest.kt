package example.micronaut.service.postit

import example.micronaut.entities.mongo.postit.Comment
import example.micronaut.entities.mongo.postit.PostIt
import example.micronaut.repository.mongo.postit.PostItRepository
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

@MicronautTest(transactional = false)
class PostItServiceTest {

    @Inject
    lateinit var commentService: CommentService

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
        val postIt = PostIt(
            title = "Test PostIt",
            content = "Test content",
            childPostItIds = emptyList(),
            color = "red",
            commentIds = emptyList(),
            createdBy = "testUser"
        )
        val createdPostIt = postItService.createPostIt(postIt).block()!!
        assertNotNull(createdPostIt)
        assertNotNull(createdPostIt.id)
    }

    @Test
    fun testGetPostIt() {
        val postIt = PostIt(
            title = "Test PostIt",
            content = "Test content",
            childPostItIds = emptyList(),
            color = "red",
            commentIds = emptyList(),
            createdBy = "testUser"
        )
        val createdPostIt = postItService.createPostIt(postIt).block()!!
        val retrievedPostIt = postItService.getPostIt(createdPostIt.id!!).block()!!
        assertNotNull(retrievedPostIt)
        assertEquals(createdPostIt.id, retrievedPostIt.id)
    }

    @Test
    fun testDeletePostIt() {
        val postIt = PostIt(
            title = "Test PostIt",
            content = "Test content",
            childPostItIds = emptyList(),
            color = "red",
            commentIds = emptyList(),
            createdBy = "testUser"
        )
        val createdPostIt = postItService.createPostIt(postIt).block()!!
        val deletedCount = postItService.deletePostIt(createdPostIt.id!!).block()!!
        assertEquals(1, deletedCount)
    }

    @Test
    fun testUpdatePostIt() {
        val postIt = PostIt(
            title = "Test PostIt",
            content = "Test content",
            childPostItIds = emptyList(),
            color = "red",
            commentIds = emptyList(),
            createdBy = "testUser"
        )
        val createdPostIt = postItService.createPostIt(postIt).block()!!
        createdPostIt.title = "Updated title"
        val updatedPostIt = postItService.updatePostIt(createdPostIt).block()!!
        assertEquals("Updated title", updatedPostIt.title)
    }

    @Test
    fun testAddCommentToPostIt() {
        val postIt = PostIt(
            title = "Test PostIt",
            content = "Test content",
            childPostItIds = emptyList(),
            color = "red",
            commentIds = emptyList(),
            createdBy = "testUser"
        )
        val createdPostIt = postItService.createPostIt(postIt).block()!!
        val comment = Comment(postId = createdPostIt.id!!, title = "Test comment", content = "Test comment content", createdBy = "testUser" )
        val createdComment = commentService.createComment(comment).block()!!
        val updatedPostIt = postItService.addCommentToPostIt(createdPostIt.id!!, createdComment.id!!).block()!!
        assertTrue(updatedPostIt.commentIds.contains(createdComment.id))
    }

    @Test
    fun testRemoveCommentFromPostIt() {
        val postIt = PostIt(
            title = "Test PostIt",
            content = "Test content",
            childPostItIds = emptyList(),
            color = "red",
            commentIds = emptyList(),
            createdBy = "testUser"
        )
        val createdPostIt = postItService.createPostIt(postIt).block()!!
        val comment = Comment(postId = createdPostIt.id!!, title = "Test comment", content = "Test comment content", createdBy = "testUser")
        val createdComment = commentService.createComment(comment).block()!!
        val updatedPostIt = postItService.addCommentToPostIt(createdPostIt.id!!, createdComment.id!!).block()!!
        val updatedPostIt2 = postItService.removeCommentFromPostIt(updatedPostIt.id!!, createdComment.id!!).block()!!
        assertFalse(updatedPostIt2.commentIds.contains(createdComment.id))
    }

    @Test
    fun testClearCommentsFromPostIt() {
        val postIt = PostIt(
            title = "Test PostIt",
            content = "Test content",
            childPostItIds = emptyList(),
            color = "red",
            commentIds = emptyList(),
            createdBy = "testUser"
        )
        val createdPostIt = postItService.createPostIt(postIt).block()!!
        val comment = Comment(postId = createdPostIt.id!!, title = "Test comment", content = "Test comment content", createdBy = "testUser")
        val createdComment = commentService.createComment(comment).block()!!
        val updatedPostIt = postItService.addCommentToPostIt(createdPostIt.id!!, createdComment.id!!).block()!!
        val updatedPostIt2 = postItService.clearCommentsFromPostIt(updatedPostIt.id!!).block()!!
        assertTrue(updatedPostIt2.commentIds.isEmpty())
    }

    @Test
    fun `getPosts returns all posts`() {
        // Given
        val postIt1 = PostIt(
            title = "Test PostIt 1",
            content = "Test content 1",
            childPostItIds = emptyList(),
            color = "red",
            commentIds = emptyList(),
            createdBy = "testUser"
        )
        val postIt2 = PostIt(
            title = "Test PostIt 2",
            content = "Test content 2",
            childPostItIds = emptyList(),
            color = "blue",
            commentIds = emptyList(),
            createdBy = "testUser"
        )
        postItService.createPostIt(postIt1).block()
        postItService.createPostIt(postIt2).block()

        // When
        val posts = postItService.getPosts().collectList().block()!!

        // Then
        assertEquals(2, posts.size)
        assertTrue(posts.containsAll(listOf(postIt1, postIt2)))
    }

    @Test
    fun `getPosts returns empty list when no posts exist`() {
        // Given
        // No posts created

        // When
        val posts = postItService.getPosts().collectList().block()!!

        // Then
        assertTrue(posts.isEmpty())
    }

    @Test
    fun getByIds() {
        // Given
        val postIt1 = PostIt(
            title = "Test PostIt 1",
            content = "Test content 1",
            childPostItIds = emptyList(),
            color = "red",
            commentIds = emptyList(),
            createdBy = "testUser"
        )
        val postIt2 = PostIt(
            title = "Test PostIt 2",
            content = "Test content 2",
            childPostItIds = emptyList(),
            color = "blue",
            commentIds = emptyList(),
            createdBy = "testUser"
        )
        val createdPostIt1 = postItService.createPostIt(postIt1).block()!!
        val createdPostIt2 = postItService.createPostIt(postIt2).block()!!

        // When
        var posts = postItService.getByIds(listOf(createdPostIt1.id!!, createdPostIt2.id!!), 0, 10).block()!!.content

        // Then
        assertEquals(2, posts.size)
        assertTrue(posts.containsAll(listOf(createdPostIt1, createdPostIt2)))

        posts = postItService.getByIds(listOf(createdPostIt1.id!!, createdPostIt2.id!!), 0, 1).block()!!.content
        assertEquals(1, posts.size)
        assertEquals(createdPostIt1, posts[0])

        posts = postItService.getByIds(listOf(createdPostIt1.id!!, createdPostIt2.id!!), 1, 1).block()!!.content
        assertEquals(1, posts.size)
        assertEquals(createdPostIt2, posts[0])
    }

}