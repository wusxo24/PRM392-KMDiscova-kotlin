package com.example.kmd.data.remote.dto.auth

data class RegisterResponse(
    val userId: Int,
    val email: String,
    val token: String
)
