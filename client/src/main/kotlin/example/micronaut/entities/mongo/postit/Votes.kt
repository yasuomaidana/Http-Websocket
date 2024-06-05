package example.micronaut.entities.mongo.postit

import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable
import org.bson.types.ObjectId

@Introspected
@Serdeable
//@MappedEntity
data class Votes(
    @field:Id
    var id: ObjectId? = null,
    var likes: Int,
    var dislikes: Int
)
