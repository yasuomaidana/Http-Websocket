package example.micronaut.controller

import example.micronaut.dto.security.RefreshToken
import example.micronaut.repository.RefreshTokenRepository
import example.micronaut.security.CustomRefreshTokenGenerator
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule.IS_AUTHENTICATED

@Controller
class AuthController(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val refreshTokenG: CustomRefreshTokenGenerator
) {

    @Post("/logout")
    @Secured(IS_AUTHENTICATED)
    fun logout(@Body refreshToken: RefreshToken?): String {
        refreshToken?.let { it ->
            refreshTokenG.validate(it.refreshToken)
                .ifPresent { token: String ->
                refreshTokenRepository.findByRefreshToken(token)
                    .ifPresent {
                        it.revoked = true
                        refreshTokenRepository.update(it)
                    }
            }
        }
        return "Logged out"
    }

}