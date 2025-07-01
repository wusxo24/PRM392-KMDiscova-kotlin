package com.example.kmd.domain.usecase.cart

import com.example.kmd.domain.model.Cart
import com.example.kmd.domain.repository.ICartRepository
import javax.inject.Inject

class GetCartUseCase @Inject constructor(
    private val cartRepository: ICartRepository
) {
    suspend operator fun invoke(): Result<Cart> {
        return cartRepository.getCart()
    }
}