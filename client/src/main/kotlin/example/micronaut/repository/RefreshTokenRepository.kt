package example.micronaut.repository

import example.micronaut.entities.RefreshToken
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect.MYSQL
import io.micronaut.data.repository.CrudRepository
import java.util.Optional
import jakarta.transaction.Transactional
import jakarta.validation.constraints.NotBlank

@JdbcRepository(dialect= MYSQL)
interface RefreshTokenRepository : CrudRepository<RefreshToken, Long> {

    @Transactional
    fun save(@NotBlank username: String,
             @NotBlank refreshToken: String,
             revoked: Boolean): RefreshToken

    fun findByRefreshToken(@NotBlank refreshToken: String): Optional<RefreshToken>

    fun updateByUsername(@NotBlank username: String,
                         revoked: Boolean): Long
    fun deleteByUsername(@NotBlank username: String): Long

     fun findByUsername(username: String): List<RefreshToken>
}