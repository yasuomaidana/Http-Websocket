package example.micronaut.controller.register

import example.micronaut.dto.register.RegisterUserRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule.IS_ANONYMOUS
import io.micronaut.views.View


@Controller("/register")
@Secured(IS_ANONYMOUS)
class RegisterController: BaseRegisterController() {

    @View("registrationForm")
    override fun getRegistrationForm(): Map<String, Any>  = emptyMap()

    override fun registerUser(registerUserRequest: RegisterUserRequest): HttpResponse<String>{
        userService.registerUser(registerUserRequest)
        return HttpResponse.ok("User registered successfully.")
    }
}