package com.example.kmd.data.remote.dto.auth

data class UserDto(
    val id: String,
    val email: String,
    val user_type: String,
    val is_active: Boolean,
    val is_verified: Boolean,
    val profile_picture_url: String?,
    val user_timezone: String?,
    val registration_date: String,
    val last_login_date: String
)
