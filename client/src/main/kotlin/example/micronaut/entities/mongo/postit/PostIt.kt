package example.micronaut.entities.mongo.postit

import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import org.bson.types.ObjectId

@Introspected
@MappedEntity
data class PostIt (
    @field:Id
    var id: ObjectId? = null,
    var title: String,
    var content: String,
    val childPostItIds: List<ObjectId>,
    var color: String,
    val commentIds: List<ObjectId>
)