package example.micronaut.entities.mongo.postit

import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import org.bson.types.ObjectId

@Introspected
@MappedEntity
data class Votes(
    @field:Id
    var id: ObjectId? = null,
    var likes: Int,
    var dislikes: Int
)
