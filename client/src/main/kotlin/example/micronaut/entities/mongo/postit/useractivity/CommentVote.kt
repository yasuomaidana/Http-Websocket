package example.micronaut.entities.mongo.postit.useractivity

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import org.bson.types.ObjectId

@Introspected
@Serdeable
data class CommentVote(
    val commentId: ObjectId,
    var vote: Vote? = null,
) {
    enum class Vote {
        LIKE, DISLIKE
    }
}
