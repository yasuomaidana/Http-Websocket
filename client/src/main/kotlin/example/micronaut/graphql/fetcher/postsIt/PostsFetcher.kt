package example.micronaut.graphql.fetcher.postsIt

import example.micronaut.graphql.dto.PostItPageDTO
import example.micronaut.graphql.mapper.PostItMapper
import example.micronaut.manager.PostItManager
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import jakarta.inject.Singleton

@Singleton
class PostsFetcher(
    private val postItManager: PostItManager,
    private val postItMapper: PostItMapper
) : DataFetcher<PostItPageDTO> {
    override fun get(environment: DataFetchingEnvironment?): PostItPageDTO{
        val offset = environment?.getArgument("offset") as Int? ?: 0
        val limit = environment?.getArgument("limit") as Int? ?: 10

        val page = postItManager.getPosts(offset,limit)?.toFuture()?.get()

        return PostItPageDTO(page?.content?.map {
            postItMapper.toPostItDTO(it, limit, offset)
        }  ?: emptyList()
            , page?.totalPages?:0, page?.pageNumber?:0,
            page?.totalSize?:0)
    }
}