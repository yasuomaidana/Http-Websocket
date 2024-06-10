package example.micronaut.graphql.fetcher.postsIt

import example.micronaut.entities.mongo.postit.PostIt
import example.micronaut.manager.PostItManager
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import jakarta.inject.Singleton

@Singleton
class PostsFetcher(
    private val postItManager: PostItManager
) : DataFetcher<List<PostIt>> {
    override fun get(environment: DataFetchingEnvironment?): List<PostIt> {
        return postItManager.getPosts().collectList().toFuture().get()
    }
}