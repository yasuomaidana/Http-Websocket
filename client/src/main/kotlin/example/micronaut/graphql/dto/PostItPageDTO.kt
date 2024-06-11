package example.micronaut.graphql.dto

import io.micronaut.core.annotation.Introspected

@Introspected
data class PostItPageDTO(
    val content: List<PostItDTO> = emptyList(),
    val totalPages: Int = 0,
    val currentPage: Int = 0,
    val totalElements: Long = 0
)
