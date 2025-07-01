package com.example.kmd.data.remote.dto.cart

// This class matches the top-level response: { "message": "...", "cart_item": {...} }
data class AddItemToCartResponse(
    val message: String,
    val cart_item: SimpleCartItemDto
)

// This class matches the simple cart_item object
data class SimpleCartItemDto(
    val item_id: String,
    val child_name: String,
    val psychologist_name: String,
    val session_type: String
)