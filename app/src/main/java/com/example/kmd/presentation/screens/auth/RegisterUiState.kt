// File: RegisterUiState.kt
package com.example.kmd.presentation.screens.auth

sealed class RegisterUiState {
    object Idle : RegisterUiState()
    object Loading : RegisterUiState()
    data class Success(val email: String) : RegisterUiState()
    data class Error(val message: String) : RegisterUiState()
}
