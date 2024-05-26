package example.micronaut.security.passwordencoder

interface PasswordEncoder {
    fun encode(password: String): String
    fun matches(rawPassword: String, encodedPassword: String): Boolean
}