package example.micronaut.security

import example.micronaut.configuration.refreshtoken.CustomRefreshTokenConfigurationProperties
import example.micronaut.entities.RefreshToken
import example.micronaut.repository.RefreshTokenRepository
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.errors.OauthErrorResponseException
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent
import io.micronaut.security.token.refresh.RefreshTokenPersistence
import io.micronaut.security.errors.IssuingAnAccessTokenErrorCode.INVALID_GRANT
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import java.time.Instant

@Singleton
class CustomRefreshTokenPersistence(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val refreshTokenConfiguration: CustomRefreshTokenConfigurationProperties

    ) : RefreshTokenPersistence {

    override fun persistToken(event: RefreshTokenGeneratedEvent?) {
        if (event?.refreshToken != null && event.authentication?.name != null) {
            val payload = event.refreshToken
            val existingTokens = refreshTokenRepository.findByUsername(event.authentication.name)
            if (existingTokens.size >= 3) {
                existingTokens.
                filter { it -> it.dateCreated ==  existingTokens.minOf { it.dateCreated } }
                    .forEach {
                        it.revoked = true
                        refreshTokenRepository.update(it)
                    }
            }
            val expiresOn = Instant.now()
                .plusSeconds(refreshTokenConfiguration.expirationTime.inWholeSeconds)
            val refreshToken = RefreshToken(
                username = event.authentication.name,
                refreshToken = payload,
                expiresOn = expiresOn
            )
            refreshTokenRepository.save(refreshToken)
        }
    }

    override fun getAuthentication(refreshToken: String): Publisher<Authentication> {

        return Flux.create({ emitter: FluxSink<Authentication> ->
            val tokenOpt = refreshTokenRepository.findByRefreshToken(refreshToken)
            if (tokenOpt.isPresent) {
                val token = tokenOpt.get()
                val (_, username, _, revoked, dateCreated, expiresOn) = token
                val maxExpiresOn = dateCreated
                    .plusSeconds(refreshTokenConfiguration.maximumAge.inWholeSeconds)
                if (revoked) {
                    emitter.error(OauthErrorResponseException(INVALID_GRANT, "refresh token revoked", null))
                }
                else if(expiresOn.let { it != null && it.isBefore(Instant.now())} ||
                    maxExpiresOn.isBefore(Instant.now())
                    ) {
                    token.revoked = true
                    refreshTokenRepository.update(token)
                    emitter.error(OauthErrorResponseException(INVALID_GRANT, "refresh token expired", null))
                }
                else {
                    token.expiresOn = Instant.now().plusSeconds(refreshTokenConfiguration.expirationTime.inWholeSeconds)
                    refreshTokenRepository.update(token)
                    emitter.next(Authentication.build(username))
                    emitter.complete()
                }
            } else {
                emitter.error(OauthErrorResponseException(INVALID_GRANT, "refresh token not found", null))
            }
        }, FluxSink.OverflowStrategy.ERROR)
    }
}