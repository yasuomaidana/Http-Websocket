package example.micronaut.security

import example.micronaut.repository.UserRepository
import example.micronaut.security.passwordencoder.PasswordEncoder
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.authentication.provider.HttpRequestAuthenticationProvider

import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class CustomAuthenticationProvider<b>: HttpRequestAuthenticationProvider<HttpRequest<b>>{
    @Inject
    lateinit var userRepo: UserRepository

    @Inject
    lateinit var passwordEncoder: PasswordEncoder

    override fun authenticate(
        requestContext: HttpRequest<HttpRequest<b>>?,
        authRequest: AuthenticationRequest<String, String>?
    ): AuthenticationResponse {
        val user = authRequest?.identity?.let { userRepo.findByUsername(it) }
        val roles = user?.roles?.map { it.name.toString()}
        return if (user != null && passwordEncoder.matches(authRequest.secret, user.password)) {
            AuthenticationResponse.success(user.username, roles)
        } else {
            AuthenticationResponse.failure("Invalid username or password")
        }
    }
}