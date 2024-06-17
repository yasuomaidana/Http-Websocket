package example.micronaut.graphql.dto

import example.micronaut.entities.mongo.postit.useractivity.CommentVote
import io.micronaut.core.annotation.Introspected

@Introspected
data class UserActivityDTO(
    val username: String,
    val postIts: PostItPageDTO,
    val comments: CommentPageDTO,
    val commentVotes: List<CommentVote>,
    val public: Boolean
)
