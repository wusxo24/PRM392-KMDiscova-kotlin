package com.example.kmd.presentation.screens.auth

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val username: String) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}
