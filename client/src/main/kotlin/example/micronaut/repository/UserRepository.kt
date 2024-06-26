package example.micronaut.repository

import example.micronaut.entities.user.Role
import example.micronaut.entities.user.User
import io.micronaut.data.annotation.Join
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@JdbcRepository(dialect=Dialect.MYSQL)
interface UserRepository:PageableRepository<User, Long>{

    @Join(value = "roles", type = Join.Type.LEFT_FETCH)
    fun findByUsername(username: String): User?
    fun saveUserRole(user: User, role:Role)
}