package example.micronaut.entities.user

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.Relation
import io.micronaut.serde.annotation.Serdeable

@MappedEntity
@Serdeable
data class Profile(
    @field:Id
    @GeneratedValue
    val id: Long?,
    @Relation(value = Relation.Kind.ONE_TO_ONE)
    val user: User,
    val name: String,
    val lastName: String,
    val profilePictureUrl: String?
)
