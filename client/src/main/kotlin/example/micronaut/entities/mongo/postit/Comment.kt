package example.micronaut.entities.mongo.postit

import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import org.bson.types.ObjectId

@Introspected
@MappedEntity
data class Comment(
    @field:Id
    var id: ObjectId? = null,
    val title: String,
    val description: String?, // optional
    val votes: Votes = Votes(likes = 0, dislikes = 0)
)
