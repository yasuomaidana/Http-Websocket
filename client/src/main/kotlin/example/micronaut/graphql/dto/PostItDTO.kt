package example.micronaut.graphql.dto

import example.micronaut.entities.mongo.postit.Comment
import io.micronaut.core.annotation.Introspected
import org.bson.types.ObjectId

@Introspected
data class PostItDTO (
    var id: ObjectId? = null,
    var title: String,
    var content: String,
    val childPosts: List<PostItDTO>,
    var color: String,
    var comments: List<Comment>,
    var totalPages:Int,
    var currentPage:Int,
    var totalChildPosts:Long
)