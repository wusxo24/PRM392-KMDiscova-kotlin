package com.example.kmd.presentation.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmd.domain.model.CartItem
import com.example.kmd.domain.usecase.cart.AddToCartUseCase
import com.example.kmd.domain.usecase.cart.ClearCartUseCase
import com.example.kmd.domain.usecase.cart.GetCartItemsUseCase
import com.example.kmd.domain.usecase.cart.RemoveFromCartUseCase
import com.example.kmd.domain.usecase.cart.UpdateCartItemUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val addToCartUseCase: AddToCartUseCase,
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val updateCartItemUseCase: UpdateCartItemUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val clearCartUseCase: ClearCartUseCase
) : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    init {
        loadCartItems()
    }

    fun loadCartItems() {
        viewModelScope.launch {
            _cartItems.value = getCartItemsUseCase()
        }
    }

    fun addToCart(item: CartItem) {
        viewModelScope.launch {
            addToCartUseCase(item)
            loadCartItems()
        }
    }

    fun updateCartItem(item: CartItem) {
        viewModelScope.launch {
            updateCartItemUseCase(item)
            loadCartItems()
        }
    }

    fun removeFromCart(item: CartItem) {
        viewModelScope.launch {
            removeFromCartUseCase(item)
            loadCartItems()
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            clearCartUseCase()
            loadCartItems()
        }
    }
}
