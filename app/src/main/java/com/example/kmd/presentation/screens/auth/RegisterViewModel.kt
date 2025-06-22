package com.example.kmd.presentation.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmd.domain.usecase.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun register(email: String, password: String, confirmPassword: String) {
        _uiState.value = RegisterUiState.Loading
        viewModelScope.launch {
            val result = registerUseCase(email, password, confirmPassword)
            result.onSuccess {
                _uiState.value = RegisterUiState.Success(email)
            }.onFailure {
                _uiState.value = RegisterUiState.Error(it.message ?: "Unknown error")
            }
        }
    }
}
