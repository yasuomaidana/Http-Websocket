package example.micronaut.graphql.fetcher.postsIt

import example.micronaut.entities.mongo.postit.PostIt
import example.micronaut.manager.PostItManager
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import jakarta.inject.Singleton

@Singleton
class PostItFetcher(
    private val postItManager: PostItManager,
    ) : DataFetcher<PostIt> {
    override fun get(env: DataFetchingEnvironment): PostIt? {
        // Use the postItManager to add a comment to a Post-it
        val postItId = env.getArgument<String>("id")!!
        return postItManager.getPostIt(postItId).toFuture().get()
    }
}