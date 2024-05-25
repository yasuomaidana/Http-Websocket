package example.micronaut.repository

import example.micronaut.entities.Image
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository

@JdbcRepository(dialect = Dialect.MYSQL)
interface ImageRepository: CrudRepository<Image, Long> {
    fun findByName(name: String): Image?
    fun deleteByName(name: String): Long
    fun existsByName(name: String): Boolean

}