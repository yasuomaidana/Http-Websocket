package example.micronaut.dto.register

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Introspected
@Serdeable
data class RegisterUserRequest(
    val username: String,
    val email: String,
    val password: String
)
