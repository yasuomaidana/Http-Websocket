package example.micronaut.configuration.tokencleaning

import example.micronaut.repository.RefreshTokenRepository
import io.micronaut.context.annotation.Value
import io.micronaut.scheduling.annotation.Scheduled
import io.micronaut.transaction.annotation.Transactional
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import java.time.Instant
import java.time.temporal.ChronoUnit
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
    @Transactional
    fun cleanExpiredTokens() {
        logger.info("Cleaning expired tokens")
        val revokedTokens = refreshTokenRepository.findByRevoked(true)
        val expiredTokens = refreshTokenRepository.findByExpiresOnBefore(Instant.now())
        expiredTokens.filter { !it.revoked }.forEach{
            it.revoked = true
            refreshTokenRepository.update(it)
        }

        val tokens = revokedTokens + expiredTokens
        val cutTime = Instant.now().plus(delayDelete.inWholeSeconds, ChronoUnit.SECONDS)
        tokens.filter { it.dateCreated.isBefore(cutTime) }
            .forEach {
                refreshTokenRepository.delete(it)
                logger.info("Token ${it.id} for user ${it.username} deleted")
            }
    }

    companion object {
        private const val PREFIX = "app.token-cleaning"

        private const val DEFAULT_CLEANING = "PT12H"
        const val CLEANING_PERIOD = "$PREFIX.cleaning-period:$DEFAULT_CLEANING"

        private const val DEFAULT_DELAY = "PT10M"
        const val DELAY_DELETE = "$PREFIX.delay-delete:$DEFAULT_DELAY"
    }
}
