package example.micronaut.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule.IS_ANONYMOUS
import io.micronaut.views.View

@Controller("/")
@Secured(IS_ANONYMOUS)
class HomeController {

    @Get("/home")
    @Secured(IS_ANONYMOUS)
    @View("home")
    fun home(authentication: Authentication?): Map<String, Any> {

        var isAuthenticated = false
        var isAdmin = false
        if (authentication != null) {
            isAuthenticated = true
            isAdmin = authentication.roles.contains("admin")
        }

        return mapOf(
            "isAuthenticated" to isAuthenticated,
            "isAdmin" to isAdmin
        )
    }

    @Get("/login")
    @View("login")
    fun login(): Map<String, Any> = emptyMap()

}