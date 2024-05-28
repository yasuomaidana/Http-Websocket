package example.micronaut.mapper

import example.micronaut.entities.user.Role
import example.micronaut.entities.user.RoleEnum
import example.micronaut.repository.RoleRepository
import jakarta.inject.Inject
import org.mapstruct.*

@Mapper
abstract class RoleMapper {
    @Inject
    lateinit var roleRepository: RoleRepository

    fun toRoleEnum(roleName: String): RoleEnum? {
        return try {
            RoleEnum.valueOf(roleName)
        } catch (e: IllegalArgumentException) {
            null
        }
    }

    @Mappings(

        Mapping(target = "name", expression = "java(toRoleEnum(name))" )
    )
    abstract fun toRole(name:String): Role?


    @Condition(appliesTo=[ConditionStrategy.SOURCE_PARAMETERS])
    fun isRoleEnumValid(name: String): Boolean {
        return toRoleEnum(name) != null
    }

    @AfterMapping
    fun toRoleWithNullCheckBefore(roleName: String): Role? {
        toRoleEnum(roleName)?.let {
            return roleRepository.findByName(it)
        }
        return null
    }

}