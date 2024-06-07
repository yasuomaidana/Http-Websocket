package example.micronaut.graphql.fetcher

import example.micronaut.entities.mongo.postit.Comment
import example.micronaut.entities.mongo.postit.PostIt
import example.micronaut.manager.PostItManager
import example.micronaut.repository.postit.PostItRepository
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import jakarta.inject.Singleton

@Singleton
class PostItFetcher(
    private val postItManager: PostItManager,
    private val postItRepository: PostItRepository
    ) : DataFetcher<PostIt> {
    override fun get(env: DataFetchingEnvironment): PostIt? {
        // Use the postItManager to add a comment to a Post-it
        val postItId = env.getArgument<String>("id")!!
        return postItManager.getPostIt(postItId).toFuture().get()
    }
}