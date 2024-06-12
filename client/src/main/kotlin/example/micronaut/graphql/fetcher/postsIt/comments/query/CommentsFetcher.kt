package example.micronaut.graphql.fetcher.postsIt.comments.query

import example.micronaut.graphql.dto.CommentPageDTO
import example.micronaut.graphql.mapper.CommentMapper
import example.micronaut.manager.PostItManager
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import jakarta.inject.Singleton

@Singleton
class CommentsFetcher(
    private val postItManager: PostItManager,
    private val commentMapper: CommentMapper
): DataFetcher<CommentPageDTO> {
    override fun get(environment: DataFetchingEnvironment?): CommentPageDTO {
        val postId = environment?.getArgument<String>("postId")!!
        val offset = environment.getArgument<Int>("offset") ?: 0
        val limit = environment.getArgument<Int>("limit") ?: 10
        return postItManager.getCommentFromPostIt(postId, offset, limit)
            ?.toFuture()?.get()?.let { commentMapper.commentPageToCommentPageDTO(it) } ?: CommentPageDTO()
    }
}