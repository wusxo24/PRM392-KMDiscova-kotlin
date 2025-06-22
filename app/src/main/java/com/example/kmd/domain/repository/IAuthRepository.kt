package com.example.kmd.domain.repository

import com.example.kmd.domain.model.User

interface IAuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(email: String, password: String, confirmPassword: String): Result<User>
}
