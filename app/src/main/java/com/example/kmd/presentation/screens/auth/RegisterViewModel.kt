package com.example.kmd.presentation.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmd.domain.model.User
import com.example.kmd.domain.usecase.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    data class Success(val user: User) : RegisterState()
    data class Error(val message: String) : RegisterState()
}
@HiltViewModel
class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _registerState = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState

    fun register(email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            _registerState.value = RegisterState.Loading
            val result = registerUseCase(email, password, confirmPassword)
            _registerState.value = result.fold(
                onSuccess = { RegisterState.Success(it) },
                onFailure = { RegisterState.Error(it.message ?: "Unknown error") }
            )
        }
    }
}
