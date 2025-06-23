package com.example.kmd.data.remote.dto.auth

data class RegisterResponse(
    val message: String,
    val user: UserDto,
    val token: String
)
