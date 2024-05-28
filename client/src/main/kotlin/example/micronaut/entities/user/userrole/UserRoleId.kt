package example.micronaut.entities.user.userrole

import io.micronaut.data.annotation.Embeddable
import io.micronaut.data.annotation.MappedProperty

@Embeddable
data class UserRoleId(

    @MappedProperty("user_id")
    val userId: Long,
    @MappedProperty("role_id")
    val roleId: Long
)
