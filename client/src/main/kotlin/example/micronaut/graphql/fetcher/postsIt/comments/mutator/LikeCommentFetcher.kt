package example.micronaut.graphql.fetcher.postsIt.comments.mutator

import example.micronaut.entities.mongo.postit.Comment
import example.micronaut.entities.mongo.postit.useractivity.CommentVote
import example.micronaut.exception.UserNotAuthenticatedException
import example.micronaut.manager.PostItManager
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import io.micronaut.security.utils.SecurityService
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.bson.types.ObjectId

@Singleton
class LikeCommentFetcher(
    private val postItManager: PostItManager,
    @Inject
    private val securityService: SecurityService
): DataFetcher<Comment> {
    override fun get(environment: DataFetchingEnvironment): Comment {
        val id = environment.getArgument<String>("id")!!
        val username = securityService.username().orElseGet { throw UserNotAuthenticatedException() }
        return postItManager.voteComment(ObjectId(id), username, CommentVote.VoteType.LIKE).toFuture().get()
    }

}