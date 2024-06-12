package example.micronaut.graphql.fetcher.postsIt.comments.mutator

import example.micronaut.entities.mongo.postit.Comment
import example.micronaut.manager.PostItManager
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import jakarta.inject.Singleton

@Singleton
class CreateCommentFetcher(private val postItManager: PostItManager) : DataFetcher<Comment> {
    override fun get(env: DataFetchingEnvironment): Comment? {
        val postId = env.getArgument<String>("postId")!!
        val title = env.getArgument<String>("title")!!
        val content = env.getArgument<String>("content")
        val comment = Comment(postId, title, content)
        return postItManager.addCommentToPostIt(postId, comment).thenReturn(comment).toFuture().get()
    }
}