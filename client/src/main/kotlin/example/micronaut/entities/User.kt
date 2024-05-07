package example.micronaut.entities

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable

@MappedEntity
@Serdeable
data class User(
    @field:Id
    @GeneratedValue
    val id: Long?,
    val username: String,
    val email: String,
    val password: String,
    val roles:List<String>? = null
)
