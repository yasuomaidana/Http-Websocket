package example.micronaut.entities.user

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.Join
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.Relation
import io.micronaut.data.annotation.sql.JoinColumn
import io.micronaut.data.annotation.sql.JoinTable
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

    @JoinTable(name = "user_roles" , joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")])
    @Relation(value = Relation.Kind.MANY_TO_MANY, cascade =
    [Relation.Cascade.ALL], mappedBy = "user")
    val roles: List<Role>?,

    @Relation(value = Relation.Kind.ONE_TO_ONE)
    val profile: Profile?
)
