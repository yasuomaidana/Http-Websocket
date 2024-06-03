package example.micronaut.security

import example.micronaut.configuration.refreshtoken.CustomRefreshTokenConfigurationProperties
import example.micronaut.entities.RefreshToken
import example.micronaut.repository.RefreshTokenRepository
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.errors.OauthErrorResponseException
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import io.micronaut.security.errors.IssuingAnAccessTokenErrorCode.INVALID_GRANT
import org.junit.jupiter.api.Assertions.*
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import java.time.Instant
import java.util.*

class CustomRefreshTokenPersistenceTest {

    private lateinit var customRefreshTokenPersistence: CustomRefreshTokenPersistence

    @Mock
    private lateinit var refreshTokenRepository: RefreshTokenRepository
    @Mock
    private lateinit var refreshTokenConfiguration: CustomRefreshTokenConfigurationProperties

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        customRefreshTokenPersistence =
            CustomRefreshTokenPersistence(refreshTokenRepository,
                refreshTokenConfiguration)

    }

//    @Test
//    fun testGetAuthenticationRevokedToken() {
//        val refreshToken = RefreshToken(
//            username = "testUser",
//            refreshToken = "testRefreshToken",
//            revoked = true
//        )
//        Mockito.`when`(refreshTokenRepository.findByRefreshToken("testRefreshToken")).thenReturn(Optional.of(refreshToken))
//
//        val authentication = customRefreshTokenPersistence.getAuthentication("testRefreshToken").blockFirst()
//        Assertions.assertNull(authentication)
//    }
//
//    @Test
//    fun testGetAuthenticationExpiredToken() {
//        val refreshToken = RefreshToken(
//            username = "testUser",
//            refreshToken = "testRefreshToken",
//            expiresOn = Instant.now().minusSeconds(60)
//        )
//        Mockito.`when`(refreshTokenRepository.findByRefreshToken("testRefreshToken")).thenReturn(Optional.of(refreshToken))
//
//        val authentication = customRefreshTokenPersistence.getAuthentication("testRefreshToken").blockFirst()
//        Assertions.assertNull(authentication)
//        Mockito.verify(refreshTokenRepository, times(1)).update(refreshToken)
//    }
@Test
fun testGetAuthenticationExpiredToken() {
    val refreshToken = RefreshToken(
        username = "testUser",
        refreshToken = "testRefreshToken",
        expiresOn = Instant.now().minusSeconds(60)
    )
    Mockito.`when`(refreshTokenRepository.findByRefreshToken("testRefreshToken")).thenReturn(Optional.of(refreshToken))


    var authentication: Authentication? = null
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


    assertNull(authentication)
    assertNotNull(error)
    assertTrue(error is OauthErrorResponseException)
    assertNotNull((error as OauthErrorResponseException).error)
    assertEquals(INVALID_GRANT.toString(), (error as OauthErrorResponseException).error.errorCode)
    assertEquals("refresh token expired", (error as OauthErrorResponseException).errorDescription)
    Mockito.verify(refreshTokenRepository, times(1)).update(refreshToken)
}
//
//    @Test
//    fun testGetAuthenticationValidToken() {
//        val refreshToken = RefreshToken(
//            username = "testUser",
//            refreshToken = "testRefreshToken",
//            expiresOn = Instant.now().plusSeconds(60)
//        )
//        Mockito.`when`(refreshTokenRepository.findByRefreshToken("testRefreshToken")).thenReturn(Optional.of(refreshToken))
//
//        val authentication = customRefreshTokenPersistence.getAuthentication("testRefreshToken").blockFirst()
//        Assertions.assertNotNull(authentication)
//        Assertions.assertEquals("testUser", authentication.name)
//        Mockito.verify(refreshTokenRepository, times(1)).update(refreshToken)
//    }
//
//    @Test
//    fun testGetAuthenticationNonExistingToken() {
//        Mockito.`when`(refreshTokenRepository.findByRefreshToken("nonExistingToken")).thenReturn(Optional.empty())
//
//        val authentication = customRefreshTokenPersistence.getAuthentication("nonExistingToken").blockFirst()
//        Assertions.assertNull(authentication)
//    }

    @Test
    fun testPersistToken() {
        val event = RefreshTokenGeneratedEvent(Authentication.build("testUser"),
            "testRefreshToken")
        customRefreshTokenPersistence.persistToken(event)

        Mockito.verify(refreshTokenRepository, times(1)).save(any())
    }
}