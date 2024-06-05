package example.micronaut.service.postit

import example.micronaut.entities.mongo.postit.Votes
import example.micronaut.repository.postit.comments.votes.VotesRepository
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

@MicronautTest(transactional = false)
class VotesServiceTest {
    @Inject
    lateinit var votesService: VotesService
    @Inject
    lateinit var votesRepository: VotesRepository

    @BeforeEach
    fun setUp() {
        votesRepository.deleteAll().block()
    }

    @AfterEach
    fun cleanUp() {
        votesRepository.deleteAll().block()
    }

    @Test
    fun createVote() {
        val votes = Votes( likes = 1, dislikes =  1)
        val result = votesService.createVote(votes).block()
        assertNotNull(result)
        assertEquals(votes.likes, result?.dislikes)
        assertEquals(votes.dislikes, result?.dislikes)
    }

    @Test
    fun getVote() {
        val votes = Votes(likes = 1, dislikes = 1)
        val createdVote = votesService.createVote(votes).block()
        val result = votesService.getVotes(createdVote?.id!!)
        assertNotNull(result)
        assertEquals(votes.likes, result.get().likes)
        assertEquals(votes.dislikes, result.get().dislikes)
    }

    @Test
    fun deleteVote() {
        val votes = Votes(likes = 1, dislikes = 1)
        val createdVote = votesService.createVote(votes).block()
        votesService.deleteVote(createdVote?.id!!).block()
        val result = votesService.getVotes(createdVote.id!!).orElse(null)
        assertNull(result)
    }

    @Test
    fun updateVote() {
        val votes = Votes(likes = 1, dislikes = 1)
        val createdVote = votesService.createVote(votes).block()
        val updatedVote = Votes(id = createdVote?.id!!, likes = 2, dislikes = 2)
        val result = votesService.updateVote(updatedVote).block()
        assertNotNull(result)
        assertEquals(updatedVote.likes, result?.likes)
        assertEquals(updatedVote.dislikes, result?.dislikes)
    }
}