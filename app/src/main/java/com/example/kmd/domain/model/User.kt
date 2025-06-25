package com.example.kmd.domain.model


data class User(
    val id: String,
    val email: String,
    val userType: UserType,
    val isVerified: Boolean
)
