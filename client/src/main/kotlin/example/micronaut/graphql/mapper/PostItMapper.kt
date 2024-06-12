package example.micronaut.graphql.mapper

import example.micronaut.entities.mongo.postit.PostIt
import example.micronaut.graphql.dto.CommentPageDTO
import example.micronaut.graphql.dto.PostItDTO
import example.micronaut.graphql.dto.PostItPageDTO
import example.micronaut.manager.PostItManager
import io.micronaut.data.model.Page
import jakarta.inject.Inject
import org.bson.types.ObjectId
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import reactor.core.publisher.Flux

@Mapper
abstract class PostItMapper {

    @Inject
    lateinit var postItManager: PostItManager

    @Inject
    lateinit var commentMapper: CommentMapper

    @Mappings(
        Mapping(target = "childPosts", expression = "java(postItPageToDTO(childPosts, kids))"),
        Mapping(target = "comments", expression="java(commentIdsToCommentsPage(postId.getCommentIds(), kids))"),
        Mapping(target = "id", source = "postId.id"),
        Mapping(target = "content", source = "postId.content"),
    )
    abstract fun postItToPostItDTO(postId: PostIt, childPosts: Page<PostIt>,kids:Int): PostItDTO

    fun postItToPostItDTO(postIt:PostIt, kids:Int=10, offset:Int=0): PostItDTO {
        val postsPages = postItManager.getPostsByIds(postIt.childPostItIds, offset, kids).toFuture().get()

        return postItToPostItDTO(postIt, postsPages,kids)
    }

    @Mappings(
        Mapping(target = "content", expression = "java(postItPageToListDTO(postsPage, postsLimit))"),
        Mapping(target = "totalPages", expression = "java(postsPage.getTotalPages())"),
        Mapping(target = "currentPage", expression = "java(postsPage.getPageNumber())"),
        Mapping(target = "totalPosts", expression = "java(postsPage.getTotalSize())")
    )
    abstract fun postItPageToDTO(postsPage:Page<PostIt>, postsLimit: Int): PostItPageDTO

    fun postItPageToListDTO(childPosts: Page<PostIt>, kids: Int): List<PostItDTO> {
        return Flux.fromIterable(childPosts)
            .map { postItToPostItDTO(it, kids, 0) }
            .collectList()
            .toFuture()
            .get()
    }

    fun commentIdsToCommentsPage(commentIds: List<ObjectId>, commentLimit:Int): CommentPageDTO
    =
        postItManager.getComments(commentIds, 0, commentLimit).toFuture().get().let {
             commentMapper.commentPageToCommentPageDTO(it)
        }

}