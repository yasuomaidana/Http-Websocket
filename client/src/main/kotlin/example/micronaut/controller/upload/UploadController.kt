package example.micronaut.controller.upload

import example.micronaut.entities.Image
import example.micronaut.repository.ImageRepository
import io.micronaut.http.MediaType.*
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Part
import io.micronaut.http.annotation.Post
import io.micronaut.http.multipart.CompletedFileUpload
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule.IS_ANONYMOUS
import java.io.ByteArrayInputStream
import java.net.URLConnection

@Controller("/upload")
@Secured(IS_ANONYMOUS)
class UploadController (
    private val imageRepository: ImageRepository
){

    private val allowedMimeTypes = listOf(IMAGE_JPEG, IMAGE_PNG, IMAGE_GIF)

    @Post("/file", consumes = [MULTIPART_FORM_DATA])
    fun uploadFile(@Part file: CompletedFileUpload): String {
        val fileName = file.filename
        val bytes = file.bytes
        val image = Image(null, fileName, bytes)
        // Validate the image file type
        val mimeType = getMimeType(bytes)
        if (!allowedMimeTypes.contains(mimeType) or !image.isValidImageExtension()) {
            throw IllegalArgumentException("Invalid file type: $mimeType")
        }
        imageRepository.save(image)
        return "Image uploaded successfully"
    }
}

private fun getMimeType(bytes: ByteArray): String {
    val byteArrayInputStream = ByteArrayInputStream(bytes)
    val mimeType = URLConnection.guessContentTypeFromStream(byteArrayInputStream)
    return mimeType ?: APPLICATION_OCTET_STREAM
}