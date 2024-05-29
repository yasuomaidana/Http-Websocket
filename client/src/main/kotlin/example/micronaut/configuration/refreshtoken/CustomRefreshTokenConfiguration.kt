package example.micronaut.configuration.refreshtoken
import io.micronaut.security.token.jwt.generator.RefreshTokenConfiguration
import kotlin.time.Duration

interface CustomRefreshTokenConfiguration: RefreshTokenConfiguration {

    var expirationTime: Duration

    fun setExpirationTime(expirationTime: String) {
        this.expirationTime = Duration.parse(expirationTime)
    }

}