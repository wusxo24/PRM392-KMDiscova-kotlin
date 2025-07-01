package com.example.kmd.data.repository

import com.example.kmd.data.mapper.toDomain
import com.example.kmd.data.remote.api.CartApiService
import com.example.kmd.domain.model.Cart
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
}