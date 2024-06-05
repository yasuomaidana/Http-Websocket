package example.micronaut.service

import example.micronaut.entities.mongo.postit.Votes
import example.micronaut.repository.VotesGetRepository
import example.micronaut.repository.VotesRepository
import io.micronaut.core.annotation.NonNull
import jakarta.inject.Singleton
import org.bson.types.ObjectId
import reactor.core.publisher.Mono
import java.util.*

@Singleton
class VotesService(
    private val votesRepository: VotesRepository,
    private val votesGetRepository: VotesGetRepository
) {
    fun createVote(vote: Votes): Mono<Votes> {
        return votesRepository.save(vote)
    }

    fun getVotes(id: ObjectId): Optional<Votes> = votesGetRepository.findById(id)

    fun deleteVote(id: ObjectId):Mono<Long> {
        return votesRepository.deleteById(id)
    }

    fun updateVote(updatedVote: Votes): Mono<Votes> {
        return votesRepository.update(updatedVote)
    }

}