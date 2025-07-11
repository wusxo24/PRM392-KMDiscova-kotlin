package com.example.kmd.data.repository

import com.example.kmd.data.local.preferences.PreferencesManager
import com.example.kmd.data.mapper.AuthMapper
import com.example.kmd.data.mapper.AuthMapper.toDomainModel
import com.example.kmd.data.remote.api.AuthApiService
import com.example.kmd.data.remote.dto.auth.LoginRequest
import com.example.kmd.data.remote.dto.auth.LoginResponse
import com.example.kmd.data.remote.dto.auth.RegisterRequest
import com.example.kmd.domain.model.User
import com.example.kmd.domain.model.AuthResult
import com.example.kmd.domain.repository.IAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import java.io.IOException
import retrofit2.HttpException
@Singleton
class AuthRepository @Inject constructor(
    private val authApiService: AuthApiService,
    private val preferencesManager: PreferencesManager
) : IAuthRepository {

    override suspend fun login(email: String, password: String): Result<AuthResult> {
        return try {
            val request = LoginRequest(email, password)
            val response = authApiService.login(request)
            Result.success(response.toDomainModel())
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> Result.failure(Exception("Invalid email or password"))
                400 -> Result.failure(Exception("Invalid request format"))
                else -> Result.failure(Exception("Login failed: ${e.message()}"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Network error. Please check your connection."))
        } catch (e: Exception) {
            Result.failure(Exception("An unexpected error occurred: ${e.message}"))
        }
    }

    override suspend fun register(email: String, password: String, passwordConfirm: String): Result<AuthResult> {
        return try {
            val request = RegisterRequest(
                email = email,
                password = password,
                password_confirm = passwordConfirm
            )
            val response = authApiService.register(request)
            Result.success(response.toDomainModel())
        } catch (e: HttpException) {
            when (e.code()) {
                400 -> Result.failure(Exception("Invalid registration data"))
                409 -> Result.failure(Exception("Email already exists"))
                else -> Result.failure(Exception("Registration failed: ${e.message()}"))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Network error. Please check your connection."))
        } catch (e: Exception) {
            Result.failure(Exception("An unexpected error occurred: ${e.message}"))
        }
    }


//    override suspend fun logout(): Result<Unit> {
//        return try {
//            authApiService.logout()
//            Result.success(Unit)
//        } catch (e: Exception) {
//            // Even if server logout fails, we should clear local data
//            Result.success(Unit)
//        }
//    }

    override suspend fun getCurrentUser(): User? {
        // Implementation depends on how you store current user
        // Could be from preferences or from a cached response
        return null
    }

    override suspend fun saveAuthToken(token: String) {
        preferencesManager.saveAuthToken(token)
    }

    override suspend fun getAuthToken(): String? {
        return preferencesManager.getAuthToken()
    }

    override suspend fun clearAuthData() {
        preferencesManager.clearAuthToken()
        // Clear any other auth-related data
    }
}



