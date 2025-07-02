package com.example.kmd.data.remote.api

import com.example.kmd.data.remote.dto.cart.AddItemToCartResponse
import com.example.kmd.data.remote.dto.cart.AddToCartRequest
import com.example.kmd.data.remote.dto.cart.CartDto
import com.example.kmd.domain.model.CheckoutRequest
import com.example.kmd.domain.model.CheckoutResponse
import com.example.kmd.domain.model.InitiatePaymentRequest
import com.example.kmd.domain.model.PaymentInitiationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CartApiService {
    @GET("/api/carts/my_cart/")
    suspend fun getCart(): CartDto

    @POST("/api/carts/add_item/")
    suspend fun addItemToCart(@Body request: AddToCartRequest): AddItemToCartResponse

    @DELETE("/api/carts/items/{id}/remove/")
    suspend fun removeItemFromCart(@Path("id") itemId: String): Response<Unit>

    @POST("api/carts/items/{id}/checkout/")

    suspend fun checkoutCartItem(
        @Path("id") itemId: String,
        @Body request: CheckoutRequest
    ): CheckoutResponse
    @POST("api/payments/orders/{order_id}/initiate_payment/")
    suspend fun initiatePayment(
        @Path("order_id") orderId: String,
        @Body body: InitiatePaymentRequest
    ): PaymentInitiationResponse


}