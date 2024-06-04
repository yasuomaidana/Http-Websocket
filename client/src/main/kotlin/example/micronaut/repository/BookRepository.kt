package example.micronaut.repository

import example.micronaut.entities.Book
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@JdbcRepository(dialect= Dialect.MYSQL)
interface BookRepository: PageableRepository<Book, Long> {
    fun findByTitle(title: String): Book?
}
