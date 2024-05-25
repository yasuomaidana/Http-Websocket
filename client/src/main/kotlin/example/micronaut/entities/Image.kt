package example.micronaut.entities

import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity

@MappedEntity
@Introspected
data class Image(
    @field:Id
    @GeneratedValue
    val id: Long? = null,
    val name: String,
    val data: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Image

        if (id != other.id) return false
        if (name != other.name) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }

    fun isValidImageExtension(): Boolean {
        val extension = name.substringAfterLast('.')
        return when (extension.lowercase()) {
            "jpg", "jpeg", "png", "gif" -> true
            else -> false
        }
    }
}