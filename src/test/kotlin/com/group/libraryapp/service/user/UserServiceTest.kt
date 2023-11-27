package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val userService: UserService
) {

    @AfterEach
    fun tearDown() {
        userRepository.deleteAllInBatch()
    }

    @Test
    fun saveUserTest() {
        //given
        val request = UserCreateRequest("이영진", null)

        //when
        userService.saveUser(request)

        //then
        val results = userRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("이영진")
        assertThat(results[0].age).isNull()
    }

    @Test
    fun loadUsersTest() {

        //given
        userRepository.saveAll(
            listOf(
                User("이영진", 20),
                User("ABC", null)
            )
        )

        //when
        val results = userService.loadUsers()

        //then
        assertThat(results)
            .hasSize(2).extracting("name").containsExactlyInAnyOrder("이영진", "ABC")
        assertThat(results).extracting("age").containsExactlyInAnyOrder(20, null)
    }

    @Test
    fun updateUserNameTest() {
        //given
        val savedUser = userRepository.save(User("A", 29))
        val request = UserUpdateRequest(savedUser.id, "B")

        //when
        userService.updateUserName(request)

        //then
        val result = userRepository.findAll()[0]
        assertThat(result.name).isEqualTo("B")
    }

    @Test
    fun deleteUserTest() {
        //given
        val savedUser = userRepository.save(User("A", 29))

        //when
        userService.deleteUser("A")

        //then
        assertThat(userRepository.findAll()).isEmpty()
    }
}