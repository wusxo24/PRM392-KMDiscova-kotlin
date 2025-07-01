// app/src/main/java/com/example/kmd/domain/usecase/cart/AddToCartUseCase.kt
package com.example.kmd.domain.usecase.cart

import com.example.kmd.domain.model.CartItem
import com.example.kmd.domain.repository.ICartRepository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val cartRepository: ICartRepository
) {
    suspend operator fun invoke(
        childId: String,
        psychologistId: String,
        sessionType: String,
        slotId: Int,
        notes: String?
    ): Result<Unit> { // Changed return type
        return cartRepository.addToCart(childId, psychologistId, sessionType, slotId, notes)
    }
}