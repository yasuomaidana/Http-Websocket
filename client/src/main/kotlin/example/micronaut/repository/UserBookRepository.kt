package example.micronaut.repository

import example.micronaut.entities.UserBook
import example.micronaut.entities.UserBookId
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@JdbcRepository(dialect= Dialect.MYSQL)
interface UserBookRepository:PageableRepository<UserBook, UserBookId> {
    fun save(entity: UserBook): UserBook
    fun findByUserId(userId: Long): List<UserBook>
}