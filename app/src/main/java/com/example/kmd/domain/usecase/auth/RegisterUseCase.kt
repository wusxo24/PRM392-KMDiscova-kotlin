package com.example.kmd.domain.usecase.auth

import com.example.kmd.domain.model.User
import com.example.kmd.domain.repository.IAuthRepository

class RegisterUseCase(
    private val repository: IAuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        confirmPassword: String
    ): Result<User> {
        return repository.register(email, password, confirmPassword)
    }
}
