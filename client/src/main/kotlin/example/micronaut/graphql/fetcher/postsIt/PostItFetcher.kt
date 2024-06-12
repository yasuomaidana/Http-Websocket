package example.micronaut.graphql.fetcher.postsIt

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
        return postItMapper.postItToPostItDTO(postItManager.getPostIt(postItId).toFuture().get())
    }
}