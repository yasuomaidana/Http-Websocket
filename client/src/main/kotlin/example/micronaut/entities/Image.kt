package example.micronaut.entities

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity

@MappedEntity
data class Image(
    @field:Id
    @GeneratedValue
    val id: Long? = null,
    val name: String,
    val data: ByteArray,
    val type: String // Store the image file type (e.g., image/jpeg, image/png)
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Image

        if (id != other.id) return false
        if (name != other.name) return false
        if (!data.contentEquals(other.data)) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + data.contentHashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}
