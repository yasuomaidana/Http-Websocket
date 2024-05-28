package example.micronaut.controller.register

import example.micronaut.dto.register.AddRolesToUserRequest
import example.micronaut.dto.register.RegisterUserRequest
import example.micronaut.entities.user.ADMIN_ROLE
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED
import io.micronaut.views.View

@Controller("/register/admin")
@Secured(ADMIN_ROLE)
class AdminRegisterController:BaseRegisterController() {

    @Delete("/delete")
    @Secured(IS_AUTHENTICATED)
    fun deleteUser(authentication: Authentication): HttpResponse<String> {
        userService.deleteUserByUsername(authentication.name)
        return HttpResponse.ok("${authentication.name} deleted successfully.")
    }

    @View("registrationForm")
    override fun getRegistrationForm(): Map<String, Any>  = mapOf("isAdmin" to true)

    override fun registerUser(registerUserRequest: RegisterUserRequest): HttpResponse<String> {
        userService.registerUserWithRoles(registerUserRequest)
        return HttpResponse.ok("User with roles registered successfully.")
    }

    @Put("/add-roles")
    fun addRolesToUser(@Body addRolesToUserRequest: AddRolesToUserRequest): HttpResponse<String> {
        userService.addRolesToUser(addRolesToUserRequest.username, addRolesToUserRequest.roles)
        return HttpResponse.ok("User with roles registered successfully.")
    }
}