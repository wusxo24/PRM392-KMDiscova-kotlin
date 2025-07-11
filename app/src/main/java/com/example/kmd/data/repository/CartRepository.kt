package com.example.kmd.data.repository

import com.example.kmd.data.mapper.toDomain
import com.example.kmd.data.remote.api.CartApiService
import com.example.kmd.data.remote.dto.cart.AddToCartRequest
import com.example.kmd.domain.model.Cart
import com.example.kmd.domain.model.CartItem
import com.example.kmd.domain.model.CheckoutRequest
import com.example.kmd.domain.model.CheckoutResponse
import com.example.kmd.domain.model.InitiatePaymentRequest
import com.example.kmd.domain.model.PaymentInitiationResponse
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
    override suspend fun checkoutItem(itemId: String, request: CheckoutRequest): Result<CheckoutResponse> {
        return try {
            val response = cartApiService.checkoutCartItem(itemId, request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    override suspend fun initiatePayment(
        orderId: String,
        successUrl: String,
        cancelUrl: String,
        provider: String
    ): Result<PaymentInitiationResponse> {
        return try {
            val req = InitiatePaymentRequest(
                success_url = successUrl,
                cancel_url = cancelUrl,
                provider = provider
            )
            val resp = cartApiService.initiatePayment(orderId, req)
            Result.success(resp)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}