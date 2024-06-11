package example.micronaut.graphql.mapper

import example.micronaut.entities.mongo.postit.Comment
import example.micronaut.entities.mongo.postit.PostIt
import example.micronaut.graphql.dto.PostItDTO
import example.micronaut.graphql.dto.PostItPageDTO
import example.micronaut.manager.PostItManager
import io.micronaut.data.model.Page
import jakarta.inject.Inject
import org.mapstruct.Named
import org.bson.types.ObjectId
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import reactor.core.publisher.Flux

@Mapper
abstract class PostItMapper {

    @Inject
    lateinit var postItManager: PostItManager

    @Mappings(
        Mapping(target = "childPosts", expression = "java(postItIdToPostIt(childPosts, kids))"),
        Mapping(target = "comments", source = "postId.commentIds", qualifiedByName = ["commentIdsToComments"]),
        Mapping(target = "totalPages", expression = "java(childPosts.getTotalPages())"),
        Mapping(target = "currentPage", expression = "java(childPosts.getPageNumber())"),
        Mapping(target = "id", source = "postId.id"),
        Mapping(target = "content", source = "postId.content"),
        Mapping(target="totalChildPosts", expression="java(childPosts.getTotalSize())")
    )
    abstract fun toPostItDTO(postId: PostIt, childPosts: Page<PostIt>,kids:Int): PostItDTO

    fun toPostItDTO(postIt:PostIt, kids:Int=10, offset:Int=0): PostItDTO {
        val postsPages = postItManager.getPostsByIds(postIt.childPostItIds, offset, kids).toFuture().get()

        return toPostItDTO(postIt, postsPages,kids)
    }

    @Mappings(
        Mapping(target = "content", expression = "java(postItIdToPostIt(postsPage, limit))"),
        Mapping(target = "totalPages", expression = "java(postsPage.getTotalPages())"),
        Mapping(target = "currentPage", expression = "java(postsPage.getPageNumber())"),
        Mapping(target = "totalPosts", expression = "java(postsPage.getTotalSize())")
    )
    abstract fun toPostItPageDTO(postsPage:Page<PostIt>, limit: Int): PostItPageDTO

    fun postItIdToPostIt(childPosts: Page<PostIt>, kids: Int): List<PostItDTO> {
        return Flux.fromIterable(childPosts)
            .map { toPostItDTO(it, kids) }
            .collectList()
            .toFuture()
            .get()
    }

    @Named("commentIdsToComments")
    fun commentIdsToComments(commentIds: List<ObjectId>): List<Comment> {
        return Flux.fromIterable(commentIds)
            .flatMap { postItManager.getComment(it) }
            .collectList()
            .toFuture()
            .get()
    }
}