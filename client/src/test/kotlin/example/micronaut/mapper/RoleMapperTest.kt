package example.micronaut.mapper

import example.micronaut.entities.user.Role
import example.micronaut.entities.user.RoleEnum
import example.micronaut.repository.RoleRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class RoleMapperTest {

    @InjectMocks
    private lateinit var roleMapper: RoleMapperImpl

    @Mock
    private lateinit var roleRepository: RoleRepository

    private val adminRole = Role(1, RoleEnum.ADMIN)
    private val userRole = Role(2, RoleEnum.USER)

    init {
        MockitoAnnotations.openMocks(this)
        Mockito.`when`(roleRepository.findByName(RoleEnum.ADMIN))
            .thenReturn(Role(1, RoleEnum.ADMIN))
        Mockito.`when`(roleRepository.findByName(RoleEnum.USER))
            .thenReturn(Role(2, RoleEnum.USER))
    }

    @Test
    fun `test toRole with valid role enum`() {
        val roleName = "ADMIN"
        val role = roleMapper.toRole(roleName)
        assertNotNull(role)
        assertEquals(RoleEnum.ADMIN, role!!.name)
    }

    @Test
    fun `test toRole with invalid role enum`() {
        val roleName = "INVALID_ROLE"

        val result = roleMapper.toRole(roleName)

        assertNull(result)
    }

    @Test
    fun `test toRoles`() {
        val roleNames = listOf("ADMIN", "INVALID_ROLE", "USER")

        val result = roleMapper.toRoles(roleNames)

        assertNotNull(result)
        assertEquals(2, result.size)
        assertTrue(result.contains(adminRole))
        assertTrue(result.contains(userRole))
    }
}