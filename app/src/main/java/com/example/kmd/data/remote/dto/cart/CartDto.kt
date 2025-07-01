package com.example.kmd.data.remote.dto.cart

import com.example.kmd.data.remote.dto.auth.UserDto
import com.example.kmd.data.remote.dto.child.ChildDto
import com.example.kmd.data.remote.dto.psychologist.PsychologistDto

data class CartDto(
    val cart_id: String,
    val user: String,
    val items: List<CartItemDto>,
    val total_amount: String? // Changed to nullable String
    // Removed currency from here
)

data class CartItemDto(
    val item_id: String,
    val child: ChildDto,
    val psychologist: PsychologistDto,
    val session_type: String,
    val scheduled_start_time: String,
    val scheduled_end_time: String,
    val price: String, // Changed to String
    val currency: String // Added currency here
)