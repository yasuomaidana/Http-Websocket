package example.micronaut.repository

import example.micronaut.entities.RefreshToken
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect.MYSQL
import io.micronaut.data.repository.CrudRepository
import java.util.Optional
import jakarta.validation.constraints.NotBlank
import java.time.Instant

@JdbcRepository(dialect= MYSQL)
interface RefreshTokenRepository : CrudRepository<RefreshToken, Long> {

    fun findByRefreshToken(@NotBlank refreshToken: String): Optional<RefreshToken>

     fun findByUsername(username: String): List<RefreshToken>

     fun findByRevoked(revoked: Boolean): List<RefreshToken>

     fun findByExpiresOnBefore(expiresOn: Instant): List<RefreshToken>
}