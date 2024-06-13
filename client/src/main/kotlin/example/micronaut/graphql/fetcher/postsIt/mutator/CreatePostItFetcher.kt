package example.micronaut.graphql.fetcher.postsIt.mutator

import example.micronaut.entities.mongo.postit.PostIt
import example.micronaut.manager.PostItManager
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED
import io.micronaut.security.utils.SecurityService
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
@Secured(IS_AUTHENTICATED)
class CreatePostItFetcher(
    private val postItManager: PostItManager,
    @Inject
    private val securityService: SecurityService
): DataFetcher<PostIt?>
{
    override fun get(env: DataFetchingEnvironment?): PostIt? {
        val title = env?.getArgument<String>("title")?: throw IllegalArgumentException("Title cannot be null")
        val content = env.getArgument<String>("content")?: throw IllegalArgumentException("Content cannot be null")
        val color = env.getArgument<String>("color") ?: throw IllegalArgumentException("Color cannot be null")


        val username = securityService.username().orElseGet { throw IllegalArgumentException("User not authenticated") }


        // Create a new PostIt object with the title, content, and color fields set
        return postItManager
            .createPostIt(PostIt(null,title= title,content=  content,color= color, createdBy = username))
            .toFuture()
            .get()
    }

}