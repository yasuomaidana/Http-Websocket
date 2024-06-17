package example.micronaut.entities.mongo.postit.useractivity

import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import org.bson.types.ObjectId

@Introspected
@MappedEntity
data class UserActivity(
    @field:Id
    var id: ObjectId? = null,
    var username: String,
    var postItIds: List<ObjectId> = emptyList(),
    var commentIds: List<ObjectId> = emptyList(),
    var commentVotes: List<CommentVote> = emptyList(),
    var publicFlag: Boolean = false
){
    constructor(
        username: String
    ): this(null, username)
}
