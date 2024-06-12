package example.micronaut.graphql.mapper

import example.micronaut.entities.mongo.postit.Comment
import example.micronaut.entities.mongo.postit.PostIt
import example.micronaut.graphql.dto.CommentPageDTO
import example.micronaut.manager.PostItManager
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.*
import org.mockito.ArgumentMatchers.*
import reactor.core.publisher.Mono


class PostItMapperTest {
    @InjectMocks
    private lateinit var postItMapper: PostItMapperImpl

    @Mock
    private lateinit var commentMapper: CommentMapperImpl
    @Mock
    private lateinit var postItManager: PostItManager

    private val postIt = PostIt(
        id = ObjectId(),
        title = "Test Post-It",
        content = "Test content",
        childPostItIds = listOf(ObjectId(), ObjectId()),
        color = "red",
        commentIds = listOf(ObjectId(), ObjectId())
    )

    private val returnedPostIt = PostIt(
        id = ObjectId(),
        title = "Test Post-It Child",
        content = "Test content",
        childPostItIds = emptyList(),
        color = "green",
        commentIds = emptyList()
    )

    private val returnedComment = Comment(
        content = "Test comment",
        postId = ObjectId(),
        title = "Test title"
    )

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test toPostItDTO with valid post-it`() {

        val postItPage = Page.of(listOf(returnedPostIt, returnedPostIt), Pageable.from(0), 2)
        Mockito.`when`(postItManager.getPostsByIds(postIt.childPostItIds, 0, 10))
            .thenReturn(Mono.just(postItPage))

        Mockito.`when`(postItManager.getPostsByIds(emptyList(), 0, 10))
            .thenReturn(Mono.just(Page.of(emptyList(), Pageable.from(0), 0)))

        Mockito.`when`(postItManager.getComments(anyList<ObjectId>(), anyInt(),anyInt()))
            .thenReturn(Mono.just(Page.of(listOf(returnedComment), Pageable.from(0), 1)))

        Mockito.`when`(commentMapper.commentPageToCommentPageDTO(any<Page<Comment>>()))
            .thenReturn(CommentPageDTO(listOf(returnedComment), 1, 0, 1))

        val postItPageDTO = postItMapper.postItPageToDTO(postItPage, 10)
        assertNotNull(postItPageDTO)
        assertEquals(2, postItPageDTO.content.size)
        assertEquals(1, postItPageDTO.totalPages)
        assertEquals(0, postItPageDTO.currentPage)
        assertEquals(2, postItPageDTO.totalPosts)
    }
}