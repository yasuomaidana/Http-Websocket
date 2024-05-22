package example.micronaut.entities

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable

@MappedEntity
@Serdeable
data class Book(
    @field:Id
    @GeneratedValue
    val id: Long?,

    val title: String,
    val author: String,
    val isbn: String,
    val publicationYear: Int
)
