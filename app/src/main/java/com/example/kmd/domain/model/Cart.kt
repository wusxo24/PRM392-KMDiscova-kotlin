package com.example.kmd.domain.model

data class Cart(
        val cartId: String,
        val user: User,
        val items: List<CartItem>,
        val totalAmount: Double,

)

data class CartItem(
        val itemId: String,
        val child: Child,
        val psychologist: Psychologist,
        val sessionType: String,
        val scheduledStartTime: String,
        val scheduledEndTime: String,
        val price: Double,
        val currency: String
)