package example.micronaut.controller

import example.micronaut.entities.User
import example.micronaut.repository.UserRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED
import io.micronaut.views.View
import jakarta.annotation.security.RolesAllowed
import jakarta.inject.Inject

@Controller("/register")
@Secured("isAnonymous()")
class RegisterController {

    @Inject
    lateinit var userRepository: UserRepository

    @Get
    @View("registrationForm")
    fun getRegistrationForm(): Map<String, Any> = emptyMap()

    @Get("/admin")
    @Secured("admin")
    @View("registrationForm")
    fun getAdminRegistrationForm(): Map<String, Any> = mapOf("isAdmin" to true)

    @Post
    fun registerUser(@Body user: User): HttpResponse<String> {
        userRepository.save(user)
        return HttpResponse.ok("User registered successfully.")
    }

    @Post("/admin")
    @RolesAllowed("admin")
    fun registerUserWithRoles(@Body user: User): HttpResponse<String> {
        userRepository.save(user)
        return HttpResponse.ok("User with roles registered successfully.")
    }
}