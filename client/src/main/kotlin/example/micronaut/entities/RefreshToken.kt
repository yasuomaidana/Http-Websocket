package example.micronaut.entities

import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable
import java.time.Instant
import jakarta.validation.constraints.NotBlank

@MappedEntity
@Serdeable
data class RefreshToken(

    @field:Id
    @GeneratedValue
    var id: Long? = null,

    @NotBlank
    var username: String,

    @NotBlank
    var refreshToken: String,

    var revoked: Boolean,

    @DateCreated
    var dateCreated: Instant? = null

)
