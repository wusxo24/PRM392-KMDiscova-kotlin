package com.example.kmd.domain.usecase.auth

import com.example.kmd.domain.model.AuthResult
import com.example.kmd.domain.model.User
import com.example.kmd.domain.repository.IAuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: IAuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<AuthResult> {
        return try {
            if (email.isBlank() || password.isBlank()) {
                return Result.failure(Exception("Email and password cannot be empty"))
            }

            val result = authRepository.login(email, password)

            if (result.isSuccess) {
                val authResult = result.getOrNull()!!
                authRepository.saveAuthToken(authResult.token)
            }

            result
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
