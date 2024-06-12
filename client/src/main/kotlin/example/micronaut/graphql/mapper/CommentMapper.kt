package example.micronaut.graphql.mapper

import example.micronaut.entities.mongo.postit.Comment
import example.micronaut.graphql.dto.CommentPageDTO
import io.micronaut.data.model.Page
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper
abstract class CommentMapper {

    @Mappings(
        Mapping(target = "content", expression = "java(commentsPage.getContent())"),
        Mapping(target = "totalPages", expression = "java(commentsPage.getTotalPages())"),
        Mapping(target = "currentPage", expression = "java(commentsPage.getPageNumber())"),
        Mapping(target = "totalComments", expression = "java(commentsPage.getTotalSize())")
    )
    abstract fun toCommentPageDTO(commentsPage: Page<Comment>) : CommentPageDTO
}