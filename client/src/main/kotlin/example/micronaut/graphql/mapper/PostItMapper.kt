package example.micronaut.graphql.mapper

import example.micronaut.entities.mongo.postit.Comment
import example.micronaut.entities.mongo.postit.PostIt
import example.micronaut.graphql.dto.PostItDTO
import example.micronaut.manager.PostItManager
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
        Mapping(target = "childPosts", source = "childPostItIds", qualifiedByName = ["postItIdsToPostIts"]),
        Mapping(target = "comments", source = "commentIds", qualifiedByName = ["commentIdsToComments"])
    )
    abstract fun toPostItDTO(postId: PostIt): PostItDTO

    @Named("postItIdsToPostIts")
    fun postItIdToPostIt(postItIds: List<ObjectId>): List<PostIt> {
        return Flux.fromIterable(postItIds)
            .flatMap { postItManager.getPostIt(it) }
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