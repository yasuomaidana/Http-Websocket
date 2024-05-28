package example.micronaut.repository

import example.micronaut.entities.user.userrole.UserRoleId
import example.micronaut.entities.user.userrole.UserRoles
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect.MYSQL
import io.micronaut.data.repository.PageableRepository

@JdbcRepository(dialect= MYSQL)
interface UserRolesRepository: PageableRepository<UserRoles, UserRoleId>
{
    fun deleteByUserId(userId: Long)
}