package com.example.kmd.presentation.screens.cart

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmd.domain.model.CartItem
import com.example.kmd.domain.usecase.cart.GetCartUseCase
import com.example.kmd.domain.usecase.cart.RemoveCartItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CartDetailUiState(
    val isLoading: Boolean = false,
    val cartItem: CartItem? = null,
    val errorMessage: String? = null,
    val isDeleted: Boolean = false
)

@HiltViewModel
class CartDetailViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val removeCartItemUseCase: RemoveCartItemUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val itemId: String = savedStateHandle.get<String>("itemId")!!
    private val _uiState = MutableStateFlow(CartDetailUiState())
    val uiState: StateFlow<CartDetailUiState> = _uiState.asStateFlow()

    init {
        loadCartItem()
    }

    private fun loadCartItem() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result = getCartUseCase()
            if (result.isSuccess) {
                val item = result.getOrNull()?.items?.find { it.itemId == itemId }
                _uiState.value = _uiState.value.copy(isLoading = false, cartItem = item)
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message ?: "Failed to load item"
                )
            }
        }
    }

    fun removeCartItem() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result = removeCartItemUseCase(itemId)
            if (result.isSuccess) {
                _uiState.value = _uiState.value.copy(isLoading = false, isDeleted = true)
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = result.exceptionOrNull()?.message ?: "Failed to delete item"
                )
            }
        }
    }
}