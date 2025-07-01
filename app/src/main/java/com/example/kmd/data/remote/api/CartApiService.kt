package com.example.kmd.data.remote.api

import com.example.kmd.data.remote.dto.cart.CartDto
import retrofit2.http.GET

interface CartApiService {
    @GET("/api/carts/my_cart/")
    suspend fun getCart(): CartDto
}