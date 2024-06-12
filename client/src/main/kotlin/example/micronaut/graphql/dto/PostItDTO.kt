package example.micronaut.graphql.dto

import io.micronaut.core.annotation.Introspected
import org.bson.types.ObjectId

@Introspected
data class PostItDTO (
    var id: ObjectId? = null,
    var title: String,
    var content: String,
    val childPosts: PostItPageDTO,
    var color: String,
    var comments: CommentPageDTO,
    var totalPages:Int,
    var currentPage:Int,
    var totalChildPosts:Long
)