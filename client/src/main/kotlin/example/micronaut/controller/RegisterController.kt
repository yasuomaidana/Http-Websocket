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
import jakarta.annotation.security.RolesAllowed
import jakarta.inject.Inject

@Controller("/register")
@Secured("isAnonymous()")
class RegisterController {

    @Inject
    lateinit var userRepository: UserRepository

    @Get
    fun getRegistrationForm(): HttpResponse<String> {
        return HttpResponse.ok("This is the registration form.")
    }

    @Get("/admin")
    @Secured("admin")
    fun getAdminRegistrationForm(): HttpResponse<String> {
        return HttpResponse.ok("This is the admin registration form.")
    }

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