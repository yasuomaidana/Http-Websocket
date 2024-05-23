package example.micronaut.entities

import io.micronaut.data.annotation.Embeddable
import io.micronaut.data.annotation.EmbeddedId
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity

@Embeddable
data class UserBookId(
    val userId: Long,
    val bookId: Long
)

@MappedEntity
data class  UserBook(
    @Id @EmbeddedId val id: UserBookId,
)
