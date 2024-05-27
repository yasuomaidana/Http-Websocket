package example.micronaut.entities.user

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.MappedProperty
import io.micronaut.data.model.DataType
import io.micronaut.serde.annotation.Serdeable

@MappedEntity
@Serdeable
data class Role(
    @field:Id
    @GeneratedValue
    val id: Long?,
    @MappedProperty(type = DataType.STRING)
    val name: RoleEnum
)
