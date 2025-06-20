package com.example.kmd.domain.model

data class CartItem(
    val id: String,
    val productId: String,
    val productName: String,
    val quantity: Int,
    val price: Double,
    val imageUrl: String?
)
