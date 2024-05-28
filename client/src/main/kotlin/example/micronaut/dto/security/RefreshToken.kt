package example.micronaut.dto.security

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class RefreshToken(val refreshToken: String)