package example.micronaut.graphql.fetcher.postsIt.mutator

import example.micronaut.graphql.dto.PostItDTO
import example.micronaut.graphql.mapper.PostItMapper
import example.micronaut.manager.PostItManager
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import jakarta.inject.Singleton

@Singleton
class ChangeParentPostItFetcher(
    private val postItManager: PostItManager,
    private val postItMapper: PostItMapper
) : DataFetcher<PostItDTO> {
    override fun get(environment: DataFetchingEnvironment?): PostItDTO {
        val parentId = environment?.getArgument<String>("parentId")!!
        val childId = environment.getArgument<String>("childId")!!
        val newParentId = environment.getArgument<String>("newParentId")!!
        return postItManager.changeParentPostIt(parentId, childId, newParentId).map { postItMapper.postItToPostItDTO(it) }.toFuture().get()
    }
}