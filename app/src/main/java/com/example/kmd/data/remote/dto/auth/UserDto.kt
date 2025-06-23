package com.example.kmd.data.remote.dto.auth

data class UserDto(
    val id: String,
    val email: String,
    val user_type: String,
    val is_verified: Boolean
)