package example.micronaut.configuration.refreshtoken


import com.nimbusds.jose.JWSAlgorithm
import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requires
import io.micronaut.core.annotation.Introspected
import io.micronaut.core.util.StringUtils
import io.micronaut.security.token.jwt.generator.RefreshTokenConfigurationProperties
import io.micronaut.security.token.jwt.generator.RefreshTokenConfigurationProperties.*
import kotlin.time.Duration

@Introspected
@Replaces(RefreshTokenConfigurationProperties::class)
@Requires(property = "$PREFIX.secret")
@Requires(property = "$PREFIX.enabled", notEquals = StringUtils.FALSE)
@ConfigurationProperties(PREFIX)
class CustomRefreshTokenConfigurationProperties: CustomRefreshTokenConfiguration {

    override var expirationTime: Duration = Duration.parse(DEFAULT_EXPIRATION_TIME)

    override var maximumAge: Duration = Duration.parse(DEFAULT_MAXIMUM_AGE)

    var enabled: Boolean = DEFAULT_ENABLED
    @JvmField
    var  jwsAlgorithm: JWSAlgorithm = DEFAULT_JWS_ALGORITHM
    @JvmField
    var secret: String = ""
    @JvmField
    var base64: Boolean = DEFAULT_BASE64

    override fun getJwsAlgorithm() = jwsAlgorithm
    override fun getSecret() = secret
    override fun isBase64() = base64

    companion object {
        const val DEFAULT_EXPIRATION_TIME = "PT1H"
        const val DEFAULT_MAXIMUM_AGE = "PT2H"
    }
}