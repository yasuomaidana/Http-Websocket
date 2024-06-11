package example.micronaut.graphql.dto

import io.micronaut.core.annotation.Introspected

@Introspected
data class PostItPageDTO(
    var content: List<PostItDTO> = emptyList(),
    var totalPages: Int = 0,
    var currentPage: Int = 0,
    var totalPosts: Long = 0
)
