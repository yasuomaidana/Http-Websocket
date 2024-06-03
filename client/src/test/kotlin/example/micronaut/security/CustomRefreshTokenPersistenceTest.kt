package example.micronaut.security

import example.micronaut.configuration.refreshtoken.CustomRefreshTokenConfigurationProperties
import example.micronaut.entities.RefreshToken
import example.micronaut.repository.RefreshTokenRepository
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

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