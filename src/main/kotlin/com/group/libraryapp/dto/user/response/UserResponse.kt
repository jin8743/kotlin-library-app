package com.group.libraryapp.dto.user.response

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import lombok.Getter

data class UserResponse(
    val id: Long,
    val name: String,
    val age: Int?,
) {
    companion object {
        fun of(user: User): UserResponse {
            return UserResponse(
                id = user.id!!,
                name = user.name,
                age = user.age
            )
        }
    }
}
