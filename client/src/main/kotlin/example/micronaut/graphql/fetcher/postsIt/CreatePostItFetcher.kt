package example.micronaut.graphql.fetcher.postsIt

import example.micronaut.entities.mongo.postit.PostIt
import example.micronaut.manager.PostItManager
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import jakarta.inject.Singleton

@Singleton
class CreatePostItFetcher(
    private val postItManager: PostItManager): DataFetcher<PostIt?>
{
    override fun get(env: DataFetchingEnvironment?): PostIt? {
        val title = env?.getArgument<String>("title")?: throw IllegalArgumentException("Title cannot be null")
        val content = env.getArgument<String>("content")?: throw IllegalArgumentException("Content cannot be null")
        val color = env.getArgument<String>("color") ?: throw IllegalArgumentException("Color cannot be null")

        // Create a new PostIt object with the title, content, and color fields set
        return postItManager
            .createPostIt(PostIt(null,title= title,content=  content,color= color))
            .toFuture()
            .get()
    }

}