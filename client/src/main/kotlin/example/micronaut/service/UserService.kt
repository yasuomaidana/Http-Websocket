package example.micronaut.service

import example.micronaut.dto.register.AddRolesToUserRequest
import example.micronaut.dto.register.RegisterUserRequest
import example.micronaut.entities.Book
import example.micronaut.entities.UserBookId
import example.micronaut.entities.user.RoleEnum
import example.micronaut.entities.user.User
import example.micronaut.mapper.UserMapper
import example.micronaut.repository.*
import jakarta.inject.Singleton
import javassist.NotFoundException

@Singleton
class UserService(
    private val userRepository: UserRepository,
    private val bookRepository: BookRepository,
    private val userBookRepository: UserBookRepository,
    private val roleRepository: RoleRepository,
    private val userMapper: UserMapper,
    private val  userRolesRepository: UserRolesRepository
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
        val user = userMapper.toUser(registerUserRequest)
        return userRepository.save(user)
    }

    fun registerUserWithRoles(registerUserRequest: RegisterUserRequest):User{
        val roles = listOfNotNull(roleRepository.findByName(RoleEnum.ADMIN))
        val user = userMapper.toUser(registerUserRequest, roles)
        val savedUser =  userRepository.save(user)
        return savedUser
    }
    fun deleteUserByUsername(username: String){
        userRepository.findByUsername(username)
            .takeIf { it != null }?.let {
                userRolesRepository.deleteByUserId(it.id!!)
                userRepository.delete(it)
            }
    }
}