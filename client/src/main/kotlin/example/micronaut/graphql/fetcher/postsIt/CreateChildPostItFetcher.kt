package example.micronaut.graphql.fetcher.postsIt

import example.micronaut.entities.mongo.postit.PostIt
import example.micronaut.graphql.dto.PostItDTO
import example.micronaut.graphql.mapper.PostItMapper
import example.micronaut.manager.PostItManager
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import jakarta.inject.Singleton

@Singleton
class CreateChildPostItFetcher (
    private val postItManager: PostItManager,
    private val postItMapper: PostItMapper
) : DataFetcher<PostItDTO> {
    override fun get(environment: DataFetchingEnvironment?): PostItDTO {
        val parentId = environment?.getArgument<String>("parentId")!!
        val title = environment.getArgument("title") as String
        val content = environment.getArgument("content") as String
        val color = environment.getArgument("color") as String

        val childPostIt = PostIt(title = title, content = content, color = color)
        return postItManager.createChildPostIt(parentId, childPostIt).map { postItMapper.toPostItDTO(it) }.toFuture().get()
    }
}