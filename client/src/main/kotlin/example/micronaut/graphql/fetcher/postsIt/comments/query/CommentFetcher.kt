package example.micronaut.graphql.fetcher.postsIt.comments.query

import example.micronaut.entities.mongo.postit.Comment
import example.micronaut.manager.PostItManager
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import jakarta.inject.Singleton

@Singleton
class CommentFetcher(
    private val postItManager: PostItManager
): DataFetcher<Comment> {
    override fun get(environment: DataFetchingEnvironment?): Comment? {
        val id = environment?.getArgument<String>("id")!!
        return postItManager.getComment(id).toFuture().get()
    }
}