package example.micronaut.controller.register

import example.micronaut.dto.register.RegisterUserRequest
import example.micronaut.repository.UserRepository
import example.micronaut.security.passwordencoder.PasswordEncoder
import example.micronaut.service.UserService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import jakarta.inject.Inject

abstract class BaseRegisterController {

    @Inject
    lateinit var userService: UserService

    @Get
    abstract fun getRegistrationForm(): Map<String, Any>

    @Post
    abstract fun registerUser(@Body registerUserRequest: RegisterUserRequest):
            HttpResponse<String>
}