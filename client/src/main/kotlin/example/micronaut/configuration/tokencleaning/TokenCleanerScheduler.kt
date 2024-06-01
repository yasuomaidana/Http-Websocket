package example.micronaut.configuration.tokencleaning

import example.micronaut.repository.RefreshTokenRepository
import io.micronaut.context.annotation.Value
import io.micronaut.scheduling.annotation.Scheduled
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import kotlin.time.Duration


@Singleton
class TokenCleanerScheduler(
    private val refreshTokenRepository: RefreshTokenRepository,

) {
    @Value("\${$DELAY_DELETE}")
    var delayDeleteString: String? = null

    private val delayDelete: Duration
        get() = delayDeleteString?.let { Duration.parse(it) }?: Duration.ZERO

    private val logger = LoggerFactory.getLogger(TokenCleanerScheduler::class.java)

    @Scheduled(fixedDelay = "\${$CLEANING_PERIOD}")
    fun cleanExpiredTokens() {
        logger.info("Cleaning expired tokens $delayDeleteString $delayDelete")
    }

    companion object {
        private const val PREFIX = "app.token-cleaning"

        private const val DEFAULT_CLEANING = "PT12H"
        const val CLEANING_PERIOD = "$PREFIX.cleaning-period:$DEFAULT_CLEANING"

        private const val DEFAULT_DELAY = "PT10M"
        const val DELAY_DELETE = "$PREFIX.delay-delete:$DEFAULT_DELAY"
    }
}
