package com.example.kmd.data.remote.dto.cart

data class AddToCartRequest(
    val child_id: String,
    val psychologist_id: String,
    val session_type: String,
    val start_slot_id: Int,
    val parent_notes: String?,
    val currency: String = "USD"
)