package example.micronaut.entities.user.userrole

import io.micronaut.data.annotation.EmbeddedId
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity

@MappedEntity
data class UserRoles(
    @Id @EmbeddedId val id: UserRoleId,
)
