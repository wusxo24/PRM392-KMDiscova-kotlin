package com.example.kmd.presentation.screens.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmd.domain.repository.IAuthRepository
import com.example.kmd.domain.repository.ICartRepository
import com.example.kmd.di.notification.NotificationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import javax.inject.Inject

data class SplashUiState(
    val isCheckComplete: Boolean = false,
    val isUserLoggedIn: Boolean = false,
    val cartItemCount: Int = 0
)

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: IAuthRepository,
    private val cartRepository: ICartRepository,
    private val notificationManager: NotificationManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        viewModelScope.launch {
            try {
                Log.d("SplashViewModel", "Checking auth status...")
                val token = authRepository.getAuthToken()
                val isLoggedIn = !token.isNullOrEmpty()
                Log.d("SplashViewModel", "User logged in: $isLoggedIn")

                if (isLoggedIn) {
                    // Check cart items if user is logged in
                    checkCartItems()
                } else {
                    _uiState.value = SplashUiState(
                        isCheckComplete = true,
                        isUserLoggedIn = false
                    )
                }
            } catch (e: Exception) {
                Log.e("SplashViewModel", "Error checking auth status", e)
                _uiState.value = SplashUiState(
                    isCheckComplete = true,
                    isUserLoggedIn = false
                )
            }
        }
    }

    private suspend fun checkCartItems() {
        try {
            Log.d("SplashViewModel", "Checking cart items...")
            val cartResult = cartRepository.getCart()
            Log.d("SplashViewModel", "Cart result: $cartResult")
            
            if (cartResult.isSuccess) {
                val cart = cartResult.getOrNull()
                val itemCount = cart?.items?.size ?: 0
                Log.d("SplashViewModel", "Cart item count: $itemCount")
                
                if (itemCount > 0) {
                    Log.d("SplashViewModel", "Showing cart notification for $itemCount items")
                    // Add a small delay to ensure the app is fully loaded
                    delay(1000)
                    notificationManager.showCartNotification(itemCount)
                } else {
                    Log.d("SplashViewModel", "No items in cart")
                }
                
                _uiState.value = SplashUiState(
                    isCheckComplete = true,
                    isUserLoggedIn = true,
                    cartItemCount = itemCount
                )
            } else {
                Log.e("SplashViewModel", "Failed to get cart: ${cartResult.exceptionOrNull()}")
                _uiState.value = SplashUiState(
                    isCheckComplete = true,
                    isUserLoggedIn = true
                )
            }
        } catch (e: Exception) {
            Log.e("SplashViewModel", "Error checking cart items", e)
            _uiState.value = SplashUiState(
                isCheckComplete = true,
                isUserLoggedIn = true
            )
        }
    }
}