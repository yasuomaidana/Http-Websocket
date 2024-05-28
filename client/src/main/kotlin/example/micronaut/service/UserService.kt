package example.micronaut.service

import example.micronaut.dto.register.RegisterUserRequest
import example.micronaut.entities.Book
import example.micronaut.entities.UserBookId
import example.micronaut.entities.user.RoleEnum
import example.micronaut.entities.user.User
import example.micronaut.entities.user.userrole.UserRoleId
import example.micronaut.mapper.RoleMapper
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
    private val  userRolesRepository: UserRolesRepository,
    private val roleMapper: RoleMapper
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

    fun addRolesToUser(username: String, roleNames: List<String>): User {
        val user = userRepository.findByUsername(username) ?: throw NotFoundException("User not found")
        val roles = roleMapper.toRoles(roleNames)

        val existingRoles = user.roles ?: emptySet()
        val newRoles = roles - existingRoles

        if (newRoles.isNotEmpty()) {
            val updatedUser = userMapper.toUser(user, newRoles)
            return userRepository.update(updatedUser)
        }
        return user
    }

    fun removeRolesFromUser(username: String, roleNames: List<String>?): User {
        val user = userRepository.findByUsername(username) ?: throw NotFoundException("User not found")
        val roles = roleMapper.toRoles(roleNames ?: emptyList())

        if (roles.isEmpty()) {
            userRolesRepository.deleteByUserId(user.id!!)
        } else {
            user.roles?.filter { roles.contains(it) }?.forEach { role ->
                userRolesRepository.deleteById(UserRoleId(user.id!!, role.id!!))
            }
        }
            return user
        }
}