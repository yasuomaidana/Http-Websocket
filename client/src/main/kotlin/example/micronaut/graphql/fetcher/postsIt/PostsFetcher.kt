package example.micronaut.graphql.fetcher.postsIt

import example.micronaut.graphql.dto.PostItDTO
import example.micronaut.graphql.mapper.PostItMapper
import example.micronaut.manager.PostItManager
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import jakarta.inject.Singleton

@Singleton
class PostsFetcher(
    private val postItManager: PostItManager,
    private val postItMapper: PostItMapper
) : DataFetcher<List<PostItDTO>> {
    override fun get(environment: DataFetchingEnvironment?): List<PostItDTO> {
        val offset = environment?.getArgument("offset") as Int? ?: 0
        val limit = environment?.getArgument("limit") as Int? ?: 10

        return postItManager.getPosts(offset,limit)?.toFuture()?.get()
            ?.map { postItMapper.toPostItDTO(it, limit) }?.content?.toList() ?: emptyList()
    }
}