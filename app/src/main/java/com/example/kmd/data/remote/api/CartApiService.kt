package com.example.kmd.data.remote.api

import com.example.kmd.data.remote.dto.cart.AddItemToCartResponse
import com.example.kmd.data.remote.dto.cart.AddToCartRequest
import com.example.kmd.data.remote.dto.cart.CartDto
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
}