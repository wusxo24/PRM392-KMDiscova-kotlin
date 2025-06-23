package com.example.kmd.data.remote.dto.auth

data class LoginResponse(
    val message: String,
    val user: UserDto,
    val token: String
)