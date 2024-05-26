package example.micronaut.security.passwordencoder

import jakarta.inject.Singleton
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Singleton
class BCryptPasswordEncoder :PasswordEncoder{
    private val encoder = BCryptPasswordEncoder()
    override fun encode(password: String): String {
        return encoder.encode(password)
    }

    override fun matches(rawPassword: String, encodedPassword: String): Boolean {
        return encoder.matches(rawPassword, encodedPassword)
    }
}