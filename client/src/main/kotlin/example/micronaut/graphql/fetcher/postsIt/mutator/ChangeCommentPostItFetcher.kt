package example.micronaut.graphql.fetcher.postsIt.mutator

import example.micronaut.entities.mongo.postit.Comment
import example.micronaut.exception.NullIdException
import example.micronaut.manager.PostItManager
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import jakarta.inject.Singleton

@Singleton
class ChangeCommentPostItFetcher(
    private val postItManager: PostItManager,
) : DataFetcher<Comment> {
    override fun get(environment: DataFetchingEnvironment?): Comment {
        val commentId = environment?.getArgument<String>("id") ?: throw NullIdException("Comment")
        val newPostItId = environment.getArgument<String>("newPostItId") ?: throw NullIdException("PostIt")

        return postItManager.changeCommentPostIt(commentId, newPostItId).toFuture().get()
    }
}