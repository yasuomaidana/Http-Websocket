package example.micronaut.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule.IS_ANONYMOUS
import io.micronaut.views.View

@Controller("/")
@Secured(IS_ANONYMOUS)
class HomeController {

    @Get
    @View("home")
    fun home(): Map<String, Any> = emptyMap()
}