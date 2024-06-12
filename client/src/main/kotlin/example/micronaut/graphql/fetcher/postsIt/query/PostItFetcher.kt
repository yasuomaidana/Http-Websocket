package example.micronaut.graphql.fetcher.postsIt.query

import example.micronaut.graphql.dto.PostItDTO
import example.micronaut.graphql.mapper.PostItMapper
import example.micronaut.manager.PostItManager
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import jakarta.inject.Singleton

@Singleton
class PostItFetcher(
    private val postItManager: PostItManager,
    private val postItMapper: PostItMapper
    ) : DataFetcher<PostItDTO> {
    override fun get(env: DataFetchingEnvironment): PostItDTO {
        // Use the postItManager to add a comment to a Post-it
        val postItId = env.getArgument<String>("id")!!
        val childPostLimit = env.getArgument<Int>("childPostLimit")  ?: 10
        val commentLimit = env.getArgument<Int>("commentLimit")  ?: 10
        val childOffset = env.getArgument<Int>("childOffset") ?: 0
        val commentOffset = env.getArgument<Int>("commentOffset") ?: 0
        return postItMapper.postItToPostItDTO(postItManager.getPostIt(postItId).toFuture().get()
        ,childPostLimit,childOffset,commentLimit,commentOffset)
    }
}