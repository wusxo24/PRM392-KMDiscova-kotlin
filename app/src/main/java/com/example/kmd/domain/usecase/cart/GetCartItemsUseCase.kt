package com.example.kmd.domain.usecase.cart

import com.example.kmd.data.remote.repository.CartRepository
import com.example.kmd.domain.model.CartItem

class GetCartItemsUseCase(private val repository: CartRepository) {
    suspend operator fun invoke(): List<CartItem> {
        return repository.getCartItems()
    }
}
