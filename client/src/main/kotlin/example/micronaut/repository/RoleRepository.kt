package example.micronaut.repository

import example.micronaut.entities.user.Role
import example.micronaut.entities.user.RoleEnum
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect.MYSQL
import io.micronaut.data.repository.CrudRepository

@JdbcRepository(dialect = MYSQL)
interface RoleRepository: CrudRepository<Role, Long> {
    fun findByName(name: RoleEnum): Role?
    fun findByNameIn(names: Set<RoleEnum>): Set<Role>
}