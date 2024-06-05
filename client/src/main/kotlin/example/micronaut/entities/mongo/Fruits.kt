package example.micronaut.entities.mongo

import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import org.bson.codecs.pojo.annotations.BsonId

@Introspected
@MappedEntity
data class Fruit(
    @field:Id
    @field:GeneratedValue
    @BsonId
    var id: String? = null,
    val name:String,
    var weight:Double,
)