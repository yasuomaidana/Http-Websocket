package example.micronaut.entities.mongo.postit.useractivity

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import org.bson.types.ObjectId

@Introspected
@Serdeable
data class CommentVote(
    val commentId: ObjectId,
    var voteType: VoteType? = null,
) {
    enum class VoteType {
        LIKE, DISLIKE
    }
}
