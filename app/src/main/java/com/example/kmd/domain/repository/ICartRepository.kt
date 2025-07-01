// app/src/main/java/com/example/kmd/domain/repository/ICartRepository.kt
package com.example.kmd.domain.repository

import com.example.kmd.domain.model.Cart
import com.example.kmd.domain.model.CartItem

interface ICartRepository {
    suspend fun getCart(): Result<Cart>
    suspend fun addToCart(
        childId: String,
        psychologistId: String,
        sessionType: String,
        slotId: Int,
        notes: String?
    ): Result<Unit> // Changed return type to Result<Unit>
}