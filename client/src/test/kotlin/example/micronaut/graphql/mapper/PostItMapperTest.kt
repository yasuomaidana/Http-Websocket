package example.micronaut.graphql.mapper

import example.micronaut.entities.mongo.postit.Comment
import example.micronaut.entities.mongo.postit.PostIt
import example.micronaut.manager.PostItManager
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.*
import org.mockito.ArgumentMatchers.any
import reactor.core.publisher.Mono


class PostItMapperTest {
    @InjectMocks
    private lateinit var postItMapper: PostItMapperImpl

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

        Mockito.`when`(postItManager.getPostIt(
            any(ObjectId::class.java)?: ObjectId()))
            .thenReturn(Mono.just(returnedPostIt))

        Mockito.`when`(postItManager.getComment(any(ObjectId::class.java)?: ObjectId()))
            .thenReturn(Mono.just(returnedComment))

        val postItDTO = postItMapper.toPostItDTO(postIt)
        assertNotNull(postItDTO)
        assertEquals(2, postItDTO.childPosts.size)
        assertEquals(2, postItDTO.comments.size)
    }
}