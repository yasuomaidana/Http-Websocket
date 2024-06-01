package example.micronaut.security

import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.context.ServerRequestContext
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.token.jwt.generator.RefreshTokenConfiguration
import io.micronaut.security.token.jwt.generator.SignedRefreshTokenGenerator
import jakarta.inject.Singleton
import java.util.*

@Singleton
@Replaces(SignedRefreshTokenGenerator::class)
class CustomRefreshTokenGenerator(config: RefreshTokenConfiguration?
) : SignedRefreshTokenGenerator(config) {

    override fun generate(authentication: Authentication?, token: String?): Optional<String> {
        val serverRequest = ServerRequestContext.currentRequest<HttpRequest<*>>()
        val remember = serverRequest.get().headers.get("remember")?.toBoolean() ?: false
        return if (remember) super.generate(authentication, token) else Optional.empty()
    }
}