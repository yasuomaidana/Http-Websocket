package example.micronaut.graphql.fetcher.postsIt.comments

import example.micronaut.entities.mongo.postit.Comment
import example.micronaut.service.postit.CommentService
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import jakarta.inject.Singleton

@Singleton
class CommentFetcher(
    private val commentService: CommentService
): DataFetcher<Comment> {
    override fun get(environment: DataFetchingEnvironment?): Comment? {
        val id = environment?.getArgument<String>("id")!!
        return commentService.getComment(id).toFuture().get()
    }
}