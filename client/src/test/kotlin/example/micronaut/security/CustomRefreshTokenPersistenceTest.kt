package example.micronaut.security

import example.micronaut.configuration.refreshtoken.CustomRefreshTokenConfigurationProperties
import example.micronaut.entities.RefreshToken
import example.micronaut.repository.RefreshTokenRepository
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.errors.OauthErrorResponseException
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import io.micronaut.security.errors.IssuingAnAccessTokenErrorCode.INVALID_GRANT
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.*
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.time.Instant
import java.util.*

@MicronautTest(environments = ["test"])
class CustomRefreshTokenPersistenceTest {

    private lateinit var customRefreshTokenPersistence: CustomRefreshTokenPersistence

    @Mock
    private lateinit var refreshTokenRepository: RefreshTokenRepository

    @Inject
    private lateinit var refreshTokenConfiguration: CustomRefreshTokenConfigurationProperties

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        customRefreshTokenPersistence =
            CustomRefreshTokenPersistence(
                refreshTokenRepository,
                refreshTokenConfiguration
            )

    }

    private fun verifyError(token: String, errorCode: String, errorDescription: String) {
        var authentication: Authentication? = null
        var error: Throwable? = null
        customRefreshTokenPersistence.getAuthentication(token).subscribe(object : Subscriber<Authentication> {
            override fun onSubscribe(s: Subscription) {
                s.request(1)
            }

            override fun onNext(t: Authentication) {
                authentication = t
            }

            override fun onError(t: Throwable) {
                error = t
            }

            override fun onComplete() {
            }
        })

        assertNull(authentication)
        assertNotNull(error)
        assertTrue(error is OauthErrorResponseException)
        assertNotNull((error as OauthErrorResponseException).error)
        assertEquals(errorCode, (error as OauthErrorResponseException).error.errorCode)
        assertEquals(errorDescription, (error as OauthErrorResponseException).errorDescription)
    }

    @Test
fun testGetAuthenticationRevokedToken() {
    val refreshToken = RefreshToken(
        username = "testUser",
        refreshToken = "testRefreshToken",
        revoked = true
    )
    Mockito.`when`(refreshTokenRepository.findByRefreshToken("testRefreshToken"))
        .thenReturn(Optional.of(refreshToken))
    verifyError(refreshToken.refreshToken, INVALID_GRANT.toString(), "refresh token revoked")
}

    @Test
    fun testGetAuthenticationExpiredToken() {
        val refreshToken = RefreshToken(
            username = "testUser",
            refreshToken = "testRefreshToken",
            expiresOn = Instant.now()
                .minusSeconds(refreshTokenConfiguration
                    .expirationTime.inWholeSeconds + 1 ))
        Mockito.`when`(refreshTokenRepository.findByRefreshToken("testRefreshToken"))
            .thenReturn(Optional.of(refreshToken))

        verifyError(refreshToken.refreshToken, INVALID_GRANT.toString(), "refresh token expired")
    }

@Test
fun testGetAuthenticationValidToken() {
    val refreshToken = RefreshToken(
        username = "testUser",
        refreshToken = "testRefreshToken",
        expiresOn = Instant.now().plusSeconds(3600*6)
    )
    Mockito.`when`(refreshTokenRepository.findByRefreshToken("testRefreshToken")).thenReturn(Optional.of(refreshToken))

    var authentication: Authentication?= null
    var error: Throwable? = null

    customRefreshTokenPersistence.getAuthentication("testRefreshToken").subscribe(object : Subscriber<Authentication> {
            override fun onSubscribe(s: Subscription) {
                s.request(1)
            }

            override fun onNext(t: Authentication) {
                authentication = t
            }

            override fun onError(t: Throwable) {
                error = t
            }

            override fun onComplete() {
            }
        })
    assertNotNull(authentication)
    assertNull(error)
    Mockito.verify(refreshTokenRepository, times(1)).update(refreshToken)
}

@Test
fun testGetAuthenticationNonExistingToken() {
    Mockito.`when`(refreshTokenRepository.findByRefreshToken("nonExistingToken")).thenReturn(Optional.empty())

    verifyError("nonExistingToken", INVALID_GRANT.toString(), "refresh token not found")
}

    @Test
    fun testPersistToken() {
        val event = RefreshTokenGeneratedEvent(
            Authentication.build("testUser"),
            "testRefreshToken"
        )
        customRefreshTokenPersistence.persistToken(event)

        Mockito.verify(refreshTokenRepository, times(1)).save(any())
    }
}