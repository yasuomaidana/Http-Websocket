package example.micronaut.repository

import example.micronaut.entities.user.Profile
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect.MYSQL
import io.micronaut.data.repository.CrudRepository

@JdbcRepository(dialect = MYSQL)
interface ProfileRepository: CrudRepository<Profile, Long> {
}