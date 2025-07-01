package com.example.kmd.data.repository

import com.example.kmd.data.mapper.toDomain
import com.example.kmd.data.remote.api.CartApiService
import com.example.kmd.data.remote.dto.cart.AddToCartRequest
import com.example.kmd.domain.model.Cart
import com.example.kmd.domain.model.CartItem
import com.example.kmd.domain.repository.ICartRepository
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val cartApiService: CartApiService
) : ICartRepository {
    override suspend fun getCart(): Result<Cart> {
        return try {
            val response = cartApiService.getCart()
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addToCart(
        childId: String,
        psychologistId: String,
        sessionType: String,
        slotId: Int,
        notes: String?
    ): Result<Unit> {
        return try {
            val request = AddToCartRequest(
                child_id = childId,
                psychologist_id = psychologistId,
                session_type = sessionType,
                start_slot_id = slotId,
                parent_notes = notes
            )
            cartApiService.addItemToCart(request)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun removeCartItem(itemId: String): Result<Unit> {
        return try {
            cartApiService.removeItemFromCart(itemId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}