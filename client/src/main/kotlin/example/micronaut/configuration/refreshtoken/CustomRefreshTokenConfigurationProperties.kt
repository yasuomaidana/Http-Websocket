package example.micronaut.configuration.refreshtoken


import com.nimbusds.jose.JWSAlgorithm
import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requires
import io.micronaut.core.annotation.NonNull
import io.micronaut.core.util.StringUtils
import io.micronaut.security.token.jwt.generator.RefreshTokenConfiguration

import io.micronaut.security.token.jwt.generator.RefreshTokenConfigurationProperties
import io.micronaut.security.token.jwt.generator.RefreshTokenConfigurationProperties.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import kotlin.time.Duration

interface CustomRefreshTokenConfigurationInterface: RefreshTokenConfiguration {
    fun setExpirationTime(expirationTime: String)
}

@Replaces(RefreshTokenConfigurationProperties::class)
@Requires(property = "$PREFIX.secret")
@Requires(property = "$PREFIX.enabled", notEquals = StringUtils.FALSE)
@ConfigurationProperties(PREFIX)
class CustomRefreshTokenConfigurationProperties :CustomRefreshTokenConfigurationInterface {

    var expirationTime: Duration = Duration.ZERO
    private var enabled = DEFAULT_ENABLED

    @NonNull
    @NotBlank
    private var jwsAlgorithm: JWSAlgorithm = DEFAULT_JWS_ALGORITHM

    @NonNull
    @NotBlank
    private var secret: String = ""
    private var base64 = DEFAULT_BASE64

    fun setEnabled(enabled: Boolean) {
        this.enabled = enabled
    }

    fun setJwsAlgorithm(jwsAlgorithm: JWSAlgorithm) {
        this.jwsAlgorithm = jwsAlgorithm
    }

    fun setSecret(secret: String) {
        this.secret = secret
    }

    fun setBase64(base64: Boolean) {
        this.base64 = base64
    }

    override fun setExpirationTime(expirationTime: String) {
        this.expirationTime = Duration.parse(expirationTime)
    }

    override fun getJwsAlgorithm(): JWSAlgorithm {
        return jwsAlgorithm
    }

    @NotNull
    override fun getSecret(): String {
        return secret
    }

    override fun isBase64(): Boolean {
        return base64
    }

}