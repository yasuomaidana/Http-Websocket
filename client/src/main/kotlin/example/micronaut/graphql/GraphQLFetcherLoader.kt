package example.micronaut.graphql

import example.micronaut.graphql.fetcher.postsIt.comments.mutator.CreateCommentFetcher
import example.micronaut.graphql.fetcher.postsIt.query.PostItFetcher
import example.micronaut.graphql.fetcher.postsIt.query.PostsFetcher
import example.micronaut.graphql.fetcher.postsIt.comments.query.CommentFetcher
import example.micronaut.graphql.fetcher.postsIt.comments.query.CommentsFetcher
import example.micronaut.graphql.fetcher.postsIt.mutator.*
import graphql.schema.DataFetcher
import jakarta.inject.Singleton

@Singleton
class GraphQLFetcherLoader(
    //Queries
    private val postItFetcher: PostItFetcher,
    private val postsFetcher: PostsFetcher,
    private val commentFetcher: CommentFetcher,
    private val commentsFetcher: CommentsFetcher,
    //Mutators
    private val addChildPostItFetcher: AddChildPostItFetcher,
    private val changeParentPostItFetcher: ChangeParentPostItFetcher,
    private val createChildPostItFetcher: CreateChildPostItFetcher,
    private val createPostItFetcher: CreatePostItFetcher,
    private val removeChildPostItFetcher: RemoveChildPostItFetcher,

    private val createCommentFetcher: CreateCommentFetcher,
    ) {

    val queryDict: Map<String, DataFetcher<*>> = mapOf(
        "postIt" to postItFetcher,
        "posts" to postsFetcher,
        "comment" to commentFetcher,
        "comments" to commentsFetcher
    )

    val mutationDict: Map<String, DataFetcher<*>> = mapOf(
        "addChildPostIt" to addChildPostItFetcher,
        "changeParentPostIt" to changeParentPostItFetcher,
        "createChildPostIt" to createChildPostItFetcher,
        "createPostIt" to createPostItFetcher,
        "removeChildPostIt" to removeChildPostItFetcher,
        "createComment" to createCommentFetcher
    )
}