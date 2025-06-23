package com.example.kmd.domain.repository

import com.example.kmd.domain.model.AuthResult
import com.example.kmd.domain.model.User

interface IAuthRepository {
    suspend fun login(email: String, password: String): Result<AuthResult>
    suspend fun register(email: String, password: String): Result<AuthResult>
//    suspend fun logout(): Result<Unit>
    suspend fun getCurrentUser(): User?
    suspend fun saveAuthToken(token: String)
    suspend fun getAuthToken(): String?
    suspend fun clearAuthData()
}
