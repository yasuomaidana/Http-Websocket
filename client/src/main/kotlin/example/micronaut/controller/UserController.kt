package example.micronaut.controller

import example.micronaut.entities.User
import example.micronaut.repository.UserRepository
import io.micronaut.data.exceptions.DataAccessException
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import jakarta.inject.Inject

@Secured
@Controller("/users")
class UserController {

    @Inject
    lateinit var userRepository: UserRepository

    @Post
    @Secured("isAnonymous()")
    fun createUser(@Body user: User): HttpResponse<User> {
        return try{
            HttpResponse.created(userRepository.save(user))
        } catch (e:DataAccessException){
            HttpResponse.badRequest()
        }
    }
}