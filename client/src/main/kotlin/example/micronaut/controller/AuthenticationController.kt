package example.micronaut.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured

@Secured("isAuthenticated()")
@Controller("/auth_controller")
class AuthenticationController {
    @Get
    fun authenticate(): String {
        return "Authenticated"
    }
}