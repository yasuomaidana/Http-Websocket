package example.micronaut.graphql.fetcher.postsIt.query

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
        val childPostLimit = environment?.getArgument<Int>("limit") as Int? ?: 10
        val commentLimit = environment?.getArgument<Int>("commentLimit") as Int? ?: 10

        return postItManager.getPosts(offset,childPostLimit)?.toFuture()?.get()
            ?.let { postItMapper.postItPageToDTO(it,childPostLimit,commentLimit) } ?: PostItPageDTO()
    }
}