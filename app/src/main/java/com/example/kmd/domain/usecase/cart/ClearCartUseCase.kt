package com.example.kmd.domain.usecase.cart

import com.example.kmd.data.remote.repository.CartRepository

class ClearCartUseCase(private val repository: CartRepository) {
    suspend operator fun invoke() {
        repository.clearCart()
    }
}
