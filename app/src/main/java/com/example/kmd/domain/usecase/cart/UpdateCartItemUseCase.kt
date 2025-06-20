package com.example.kmd.domain.usecase.cart

import com.example.kmd.data.remote.repository.CartRepository
import com.example.kmd.domain.model.CartItem

class UpdateCartItemUseCase(private val repository: CartRepository) {
    suspend operator fun invoke(item: CartItem) {
        repository.updateCartItem(item)
    }
}
