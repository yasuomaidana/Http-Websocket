package example.micronaut.controller


import example.micronaut.dto.RegisterUserRequest
import example.micronaut.entities.user.User
import example.micronaut.repository.UserRepository
import example.micronaut.security.passwordencoder.PasswordEncoder
import example.micronaut.service.UserService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule.IS_ANONYMOUS
import io.micronaut.views.View
import jakarta.annotation.security.RolesAllowed
import jakarta.inject.Inject

@Controller("/register")
@Secured("isAnonymous()")
class RegisterController {

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var passwordEncoder: PasswordEncoder

    @Inject
    lateinit var userService: UserService

    @Get
    @View("registrationForm")
    fun getRegistrationForm(): Map<String, Any> = emptyMap()

    @Get("/admin")
    @Secured("admin")
    @View("registrationForm")
    fun getAdminRegistrationForm(): Map<String, Any> = mapOf("isAdmin" to true)

    @Post
    @Secured(IS_ANONYMOUS)
    fun registerUser(@Body registerUserRequest: RegisterUserRequest):
            HttpResponse<String> {
        userService.registerUserWithRoles(registerUserRequest)
        return HttpResponse.ok("User registered successfully.")
    }

    @Post("/admin")
    @RolesAllowed("admin")
    fun registerUserWithRoles(@Body registerUserRequest: RegisterUserRequest): HttpResponse<String> {
        userService.registerUserWithRoles(registerUserRequest)
        return HttpResponse.ok("User with roles registered successfully.")
    }
}