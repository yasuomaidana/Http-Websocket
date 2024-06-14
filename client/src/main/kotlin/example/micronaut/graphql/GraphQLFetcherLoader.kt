package example.micronaut.graphql

import example.micronaut.graphql.fetcher.postsIt.comments.mutator.CreateCommentFetcher
import example.micronaut.graphql.fetcher.postsIt.comments.mutator.DeleteCommentFetcher
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
    postItFetcher: PostItFetcher,
    postsFetcher: PostsFetcher,
    commentFetcher: CommentFetcher,
    commentsFetcher: CommentsFetcher,
    //Mutators
    addChildPostItFetcher: AddChildPostItFetcher,
    changeParentPostItFetcher: ChangeParentPostItFetcher,
    createChildPostItFetcher: CreateChildPostItFetcher,
    createPostItFetcher: CreatePostItFetcher,
    removeChildPostItFetcher: RemoveChildPostItFetcher,
    createCommentFetcher: CreateCommentFetcher,
    deletePostItFetcher: DeletePostItFetcher,
    deleteCommentFetcher: DeleteCommentFetcher,
    updatePostItFetcher: UpdatePostItFetcher,
    changeCommentPostItFetcher: ChangeCommentPostItFetcher
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
        "createComment" to createCommentFetcher,
        "deletePostIt" to deletePostItFetcher,
        "deleteComment" to deleteCommentFetcher,
        "updatePostIt" to updatePostItFetcher,
        "changeCommentPostIt" to changeCommentPostItFetcher
    )
}