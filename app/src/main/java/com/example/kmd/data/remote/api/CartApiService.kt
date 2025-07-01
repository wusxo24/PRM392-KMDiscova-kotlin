// app/src/main/java/com/example/kmd/data/remote/api/CartApiService.kt
package com.example.kmd.data.remote.api

import com.example.kmd.data.remote.dto.cart.AddItemToCartResponse
import com.example.kmd.data.remote.dto.cart.AddToCartRequest
import com.example.kmd.data.remote.dto.cart.CartDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CartApiService {
    @GET("/api/carts/my_cart/")
    suspend fun getCart(): CartDto

    @POST("/api/carts/add_item/")
    suspend fun addItemToCart(@Body request: AddToCartRequest): AddItemToCartResponse // Changed return type
}