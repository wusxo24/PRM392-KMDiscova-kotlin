package com.example.kmd.domain.usecase.auth

import com.example.kmd.domain.model.AuthResult
import com.example.kmd.domain.model.User
import com.example.kmd.domain.repository.IAuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: IAuthRepository
) {
    suspend operator fun invoke(email: String, password: String, passwordConfirm: String): Result<AuthResult> {
        return try {
            if (email.isBlank() || password.isBlank() || passwordConfirm.isBlank()) {
                return Result.failure(Exception("Email and passwords cannot be empty"))
            }

            if (password != passwordConfirm) {
                return Result.failure(Exception("Passwords do not match"))
            }

            val result = authRepository.register(email, password, passwordConfirm)

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
