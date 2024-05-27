package example.micronaut.mapper

import example.micronaut.dto.RegisterUserRequest
import example.micronaut.entities.user.Role
import example.micronaut.entities.user.User
import example.micronaut.security.passwordencoder.PasswordEncoder
import jakarta.inject.Inject

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper
abstract class UserMapper {

    @Inject
    lateinit var passwordEncoder: PasswordEncoder

    abstract fun toUserDto(user: User): RegisterUserRequest

    @Mapping(target = "password",
        expression = "java(passwordEncoder.encode(userRegisterUserRequest.getPassword()))")
    abstract fun toUser(userRegisterUserRequest: RegisterUserRequest): User

    @Mappings(
        Mapping(target = "password",
            expression = "java(passwordEncoder.encode(userRegisterUserRequest.getPassword()))"),
        Mapping(target = "roles", source = "roles")
    )
    abstract fun toUser(userRegisterUserRequest: RegisterUserRequest,
               roles:List<Role>): User
}