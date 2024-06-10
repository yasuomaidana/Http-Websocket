package example.micronaut.graphql.dto

import example.micronaut.entities.mongo.postit.Comment
import example.micronaut.entities.mongo.postit.PostIt
import io.micronaut.core.annotation.Introspected
import org.bson.types.ObjectId

@Introspected
data class PostItDTO (
    var id: ObjectId? = null,
    var title: String,
    var content: String,
    val childPosts: List<PostIt>,
    var color: String,
    var comments: List<Comment>
)