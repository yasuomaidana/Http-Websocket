package example.micronaut.controller

import example.micronaut.repository.ImageRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule.IS_ANONYMOUS

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
}