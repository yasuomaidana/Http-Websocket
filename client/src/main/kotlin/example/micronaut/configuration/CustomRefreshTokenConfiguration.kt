package example.micronaut.configuration

import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.core.annotation.Introspected
import io.micronaut.security.token.jwt.generator.RefreshTokenConfigurationProperties
import kotlin.time.Duration

@Introspected
@ConfigurationProperties(RefreshTokenConfigurationProperties.PREFIX)
class CustomRefreshTokenConfiguration {
    var expirationTime: Duration? = Duration.ZERO
    fun setExpirationTime(expirationTime: String) {
        this.expirationTime = Duration.parse(expirationTime)
    }
}