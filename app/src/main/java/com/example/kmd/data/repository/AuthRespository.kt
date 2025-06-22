package com.example.kmd.data.repository

import com.example.kmd.data.mapper.AuthMapper
import com.example.kmd.data.remote.api.AuthApiService
import com.example.kmd.data.remote.dto.auth.LoginRequest
import com.example.kmd.data.remote.dto.auth.RegisterRequest
import com.example.kmd.domain.model.User
import com.example.kmd.domain.repository.IAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(
    private val apiService: AuthApiService,
    private val authMapper: AuthMapper
) : IAuthRepository {

    override suspend fun login(email: String, password: String): Result<User> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.login(LoginRequest(email, password))
                val user = authMapper.mapLoginResponseToUser(response)
                Result.success(user)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun register(email: String, password: String, confirmPassword: String): Result<User> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.register(RegisterRequest(email, password, confirmPassword))
                val user = authMapper.mapRegisterResponseToUser(response)
                Result.success(user)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
