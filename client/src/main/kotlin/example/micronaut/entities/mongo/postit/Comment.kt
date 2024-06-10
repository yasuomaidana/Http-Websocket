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
    val postId: ObjectId,
    val title: String,
    var content: String?= null,
    val votes: Votes = Votes(likes = 0, dislikes = 0)
){
    constructor(
        postId: String,
        title: String,
        content: String?
    ): this(null, ObjectId(postId), title, content)
}