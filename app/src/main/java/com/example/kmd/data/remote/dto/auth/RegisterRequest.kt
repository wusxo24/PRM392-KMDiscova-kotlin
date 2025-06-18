package com.example.kmd.data.remote.dto.auth

data class RegisterRequest(
    val email: String,
    val password: String,
    val confirmPassword: String
)
