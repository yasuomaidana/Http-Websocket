package example.micronaut.controller.upload

import io.micronaut.http.MediaType.MULTIPART_FORM_DATA
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
class UploadController {

    @Post("/file", consumes = [MULTIPART_FORM_DATA])
    fun uploadFile(@Part file: CompletedFileUpload): String {
        val fileName = file.filename
        val bytes = file.bytes


        // Validate the image file type
    val mimeType = getMimeType(bytes)
    if (!mimeType.startsWith("image/")) {
        throw IllegalArgumentException("Invalid file type: $mimeType")
    }

        return "Image uploaded successfully"
    }
}

private fun getMimeType(bytes: ByteArray): String {
    val byteArrayInputStream = ByteArrayInputStream(bytes)
    val mimeType = URLConnection.guessContentTypeFromStream(byteArrayInputStream)
    return mimeType ?: "application/octet-stream"
}