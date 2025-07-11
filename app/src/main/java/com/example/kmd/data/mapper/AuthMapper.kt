package com.example.kmd.data.mapper

import com.example.kmd.data.remote.dto.auth.LoginResponse
import com.example.kmd.data.remote.dto.auth.RegisterResponse
import com.example.kmd.data.remote.dto.auth.UserDto
import com.example.kmd.domain.model.AuthResult
import com.example.kmd.domain.model.User
import com.example.kmd.domain.model.UserType

object AuthMapper {
    fun UserDto.toDomainModel(): User {
        return User(
            id = this.id ?:" ",
            email = this.email,
            userType = UserType.valueOf(this.user_type),
            isVerified = this.is_verified
        )
    }

    fun LoginResponse.toDomainModel(): AuthResult {
        return AuthResult(
            message = this.message,
            user = this.user.toDomainModel(),
            token = this.token
        )
    }

    fun RegisterResponse.toDomainModel(): AuthResult {
        return AuthResult(
            message = this.message,
            user = this.user.toDomainModel(),
            token = this.token
        )
    }

}

