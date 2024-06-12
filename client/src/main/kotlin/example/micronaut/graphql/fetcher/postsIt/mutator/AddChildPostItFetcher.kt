package example.micronaut.graphql.fetcher.postsIt.mutator

import example.micronaut.graphql.dto.PostItDTO
import example.micronaut.graphql.mapper.PostItMapper
import example.micronaut.manager.PostItManager
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import jakarta.inject.Singleton

@Singleton
class AddChildPostItFetcher(
    private val postItManager: PostItManager,
    private val postItMapper: PostItMapper
) : DataFetcher<PostItDTO> {
    override fun get(environment: DataFetchingEnvironment?): PostItDTO {
        val parentId = environment?.getArgument("parentId") as String
        val childId = environment.getArgument("childId") as String

        return postItManager.addChildPostIt(parentId, childId).map { postItMapper.postItToPostItDTO(it) }.toFuture().get()
    }
}