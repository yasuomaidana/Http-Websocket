package example.micronaut.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured

@Controller
@Secured("isAnonymous()")
class JwtTester {

    @Get("/unsecured")
    fun unsecured(): String {
        return "Unsecured"
    }

    @Secured("isAuthenticated()")
    @Get("/secured")
    fun secured(): String {
        return "Secured"
    }
}