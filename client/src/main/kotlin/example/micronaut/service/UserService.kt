package example.micronaut.service

import example.micronaut.dto.RegisterUserRequest
import example.micronaut.entities.Book
import example.micronaut.entities.UserBookId
import example.micronaut.entities.user.Role
import example.micronaut.entities.user.RoleEnum
import example.micronaut.entities.user.User
import example.micronaut.repository.BookRepository
import example.micronaut.repository.RoleRepository
import example.micronaut.repository.UserBookRepository
import example.micronaut.repository.UserRepository
import example.micronaut.security.passwordencoder.PasswordEncoder
import jakarta.inject.Singleton

@Singleton
class UserService(
    private val userRepository: UserRepository,
    private val bookRepository: BookRepository,
    private val userBookRepository: UserBookRepository,
    private val passwordEncoder: PasswordEncoder,
    private val roleRepository: RoleRepository
) {
    fun getBooksForUser(userId: Long):List<Book>{
        val userBooks = userBookRepository.findByUserId(userId)
        val bookIds = userBooks.map { bookRepository.findById(it.id.bookId).orElse(null) }
        return bookIds.filterNotNull()
    }
    fun deleteBookForUser(userId: Long, bookId: Long){
        val userBook = userBookRepository.findById(UserBookId(userId, bookId)).orElse(null)
        if(userBook != null){
            userBookRepository.delete(userBook)
        }
    }

    fun registerUser(registerUserRequest: RegisterUserRequest):User{
        val user = User(
            null,
            registerUserRequest.username,
            registerUserRequest.email,
            passwordEncoder.encode(registerUserRequest.password),
            null,
            null
        )
        return userRepository.save(user)
    }

    fun registerUserWithRoles(registerUserRequest: RegisterUserRequest):User{

        val roles = roleRepository.findByName(RoleEnum.ADMIN).let { role ->
            if(role == null){
                val newRole = roleRepository.save(Role(null, RoleEnum.ADMIN))
                listOf(newRole)
            }else{
                listOf(role)
            }
        }
        val user = User(
            null,
            registerUserRequest.username,
            registerUserRequest.email,
            passwordEncoder.encode(registerUserRequest.password),
            roles,
            null
        )
        val savedUser =  userRepository.save(user)
//        roles.forEach{ role -> userRepository.saveUserRole(savedUser, role)}
        return savedUser
    }
}