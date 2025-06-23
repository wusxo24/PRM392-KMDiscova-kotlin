package com.example.kmd.domain.model

data class AuthResult(
    val message: String,
    val user: User,
    val token: String
)