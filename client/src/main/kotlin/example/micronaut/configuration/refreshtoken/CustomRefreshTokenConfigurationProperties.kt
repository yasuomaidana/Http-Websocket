package example.micronaut.configuration.refreshtoken


import com.nimbusds.jose.JWSAlgorithm
import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requires
import io.micronaut.core.annotation.Introspected
import io.micronaut.core.util.StringUtils
import io.micronaut.security.token.jwt.generator.RefreshTokenConfiguration
import io.micronaut.security.token.jwt.generator.RefreshTokenConfigurationProperties
import io.micronaut.security.token.jwt.generator.RefreshTokenConfigurationProperties.*
import kotlin.time.Duration

interface CustomRefreshTokenConfigurationInterface: RefreshTokenConfiguration {
    fun setExpirationTime(expirationTime: String)
}

@Introspected
@Replaces(RefreshTokenConfigurationProperties::class)
@Requires(property = "$PREFIX.secret")
@Requires(property = "$PREFIX.enabled", notEquals = StringUtils.FALSE)
@ConfigurationProperties(PREFIX)
class CustomRefreshTokenConfigurationProperties: CustomRefreshTokenConfigurationInterface {

    var expirationTime: Duration = Duration.ZERO
    @JvmField
    var enabled: Boolean = DEFAULT_ENABLED
    @JvmField
    var  jwsAlgorithm: JWSAlgorithm = DEFAULT_JWS_ALGORITHM
    @JvmField
    var secret: String = ""
    @JvmField
    var base64: Boolean = DEFAULT_BASE64

    override fun setExpirationTime(expirationTime: String) {
        this.expirationTime = Duration.parse(expirationTime)
    }

    override fun getJwsAlgorithm(): JWSAlgorithm {
        return jwsAlgorithm
    }

    override fun getSecret(): String {
        return secret
    }

    override fun isBase64(): Boolean {
        return base64
    }
}