package example.micronaut.graphql.dto

import example.micronaut.entities.mongo.postit.Comment
import io.micronaut.core.annotation.Introspected

@Introspected
data class CommentPageDTO(
    var content: List<Comment> = emptyList(),
    var totalPages: Int = 0,
    var currentPage: Int = 0,
    var totalComments: Long = 0
)
