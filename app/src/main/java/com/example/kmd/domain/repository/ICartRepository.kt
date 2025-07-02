package com.example.kmd.domain.repository

import com.example.kmd.domain.model.Cart
import com.example.kmd.domain.model.CheckoutRequest
import com.example.kmd.domain.model.CheckoutResponse
import com.example.kmd.domain.model.PaymentInitiationResponse

interface ICartRepository {
    suspend fun getCart(): Result<Cart>

    suspend fun addToCart(
        childId: String,
        psychologistId: String,
        sessionType: String,
        slotId: Int,
        notes: String?
    ): Result<Unit>

    suspend fun removeCartItem(itemId: String): Result<Unit>

    suspend fun checkoutItem(itemId: String, request: CheckoutRequest): Result<CheckoutResponse>

    suspend fun initiatePayment(
        orderId: String,
        successUrl: String,
        cancelUrl: String,
        provider: String = "stripe"
    ): Result<PaymentInitiationResponse>
}
