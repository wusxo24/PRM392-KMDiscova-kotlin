package com.example.kmd.domain.usecase.cart

import com.example.kmd.domain.repository.ICartRepository
import javax.inject.Inject

class RemoveCartItemUseCase @Inject constructor(
    private val cartRepository: ICartRepository
) {
    suspend operator fun invoke(itemId: String): Result<Unit> {
        return cartRepository.removeCartItem(itemId)
    }
}