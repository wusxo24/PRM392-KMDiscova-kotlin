package com.example.kmd.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmd.domain.repository.IAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SplashUiState(
    val isCheckComplete: Boolean = false,
    val isUserLoggedIn: Boolean = false
)

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: IAuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        viewModelScope.launch {
            try {
                val token = authRepository.getAuthToken()
                val isLoggedIn = !token.isNullOrEmpty()

                _uiState.value = SplashUiState(
                    isCheckComplete = true,
                    isUserLoggedIn = isLoggedIn
                )
            } catch (e: Exception) {
                _uiState.value = SplashUiState(
                    isCheckComplete = true,
                    isUserLoggedIn = false
                )
            }
        }
    }
}