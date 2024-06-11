package example.micronaut.graphql.fetcher.postsIt

import example.micronaut.entities.mongo.postit.PostIt
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
        return postItManager.getPosts().collectList().toFuture().get()
            .map {postItMapper.toPostItDTO(it)  }
    }
}