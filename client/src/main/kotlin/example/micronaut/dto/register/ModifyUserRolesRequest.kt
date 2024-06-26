package example.micronaut.dto.register

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Introspected
@Serdeable
data class ModifyUserRolesRequest(
    val username:String,
    val roles:List<String>?
)
