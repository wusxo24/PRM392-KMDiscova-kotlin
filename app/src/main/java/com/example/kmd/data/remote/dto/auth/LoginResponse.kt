package com.example.kmd.data.remote.dto.auth

data class LoginResponse(
    val token: String,
    val userId: Int,
    val email: String
)