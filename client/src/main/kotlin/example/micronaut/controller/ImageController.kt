package example.micronaut.controller

import example.micronaut.repository.ImageRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.types.files.SystemFile
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule.IS_ANONYMOUS
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

@Controller("/images")
@Secured(IS_ANONYMOUS)
class ImageController(
    private val imageRepository: ImageRepository
) {
    @Get
    @Produces("image/jpeg")
    fun getAnyImage():HttpResponse<ByteArray>{
        imageRepository.findAll().first().let {
            return HttpResponse.ok(it.data)
        }
    }

    @Get("/{fileName}")
    fun getImage(fileName:String):HttpResponse<*>{
        val filePath = Paths.get("uploads/$fileName")
        if (Files.exists(filePath)) {
            val file = File(filePath.toUri())
            return HttpResponse.ok(SystemFile(file).attach(fileName))
        } else {
            return HttpResponse.notFound("Image not found")
        }
    }
}