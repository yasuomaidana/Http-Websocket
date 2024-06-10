package example.micronaut.graphql.fetcher

import example.micronaut.entities.mongo.postit.Comment
import example.micronaut.service.postit.CommentService
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import jakarta.inject.Singleton
import org.bson.types.ObjectId

@Singleton
class CreateCommentFetcher(private val commentService: CommentService) : DataFetcher<Comment> {
    override fun get(env: DataFetchingEnvironment): Comment? {
        val postId = env.getArgument<ObjectId>("postId")
        val title = env.getArgument<String>("title")!!
        val content = env.getArgument<String>("content")!!
        return commentService.createComment(Comment(null, postId, title, content)).block()
    }
}