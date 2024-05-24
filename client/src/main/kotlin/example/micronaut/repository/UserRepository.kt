package example.micronaut.repository

import example.micronaut.entities.User
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@JdbcRepository(dialect=Dialect.MYSQL)
interface UserRepository:PageableRepository<User, Long>{
    fun findByUsername(username: String): User?
}