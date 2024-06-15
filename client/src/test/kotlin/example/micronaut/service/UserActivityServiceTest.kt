package example.micronaut.service

import example.micronaut.entities.mongo.postit.useractivity.CommentVote
import example.micronaut.entities.mongo.postit.useractivity.UserActivity
import example.micronaut.repository.mongo.UserActivityRepository
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.bson.types.ObjectId
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

@MicronautTest(transactional = false)
class UserActivityServiceTest {

    @Inject
    lateinit var userActivityService: UserActivityService

    @Inject
    lateinit var userActivityRepository: UserActivityRepository

    @AfterEach
    fun cleanUp() {
        userActivityRepository.deleteAll().block()
    }

    @Test
    fun getUserActivity() {
        val username = "test"
        val userActivity = userActivityService.getUserActivity(username).block()
        assertNotNull(userActivity)
    }

    @Test
fun testUpdateUserActivity() {
    val username = "testUser"

    // Save the user activity
    val id = userActivityService.getUserActivity(username).block()?.id


    // Update the user activity
    val updatedPostItIds = listOf(ObjectId(), ObjectId())
    val updatedCommentIds = listOf(ObjectId(), ObjectId())
    val updatedCommentVotes = listOf(CommentVote(ObjectId(), CommentVote.Vote.DISLIKE), CommentVote(ObjectId(), CommentVote.Vote.LIKE))
    val updatedUserActivity = UserActivity(id, username, updatedPostItIds, updatedCommentIds, updatedCommentVotes)
    userActivityService.updateUserActivity(updatedUserActivity).block()

    // Retrieve the updated user activity
    val retrievedUserActivity = userActivityService.getUserActivity(username).block()

    // Check if the retrieved user activity matches the updated one
    assertEquals(updatedPostItIds, retrievedUserActivity?.postItIds)
    assertEquals(updatedCommentIds, retrievedUserActivity?.commentIds)
    assertEquals(updatedCommentVotes, retrievedUserActivity?.commentVotes)
}

    @Test
    fun deleteUserActivity() {
        val username = "test"
        val userActivity = userActivityService.getUserActivity(username).block()
        assertNotNull(userActivity)
        assertNotNull(userActivityService.deleteUserActivity(userActivity!!).block())
    }
}