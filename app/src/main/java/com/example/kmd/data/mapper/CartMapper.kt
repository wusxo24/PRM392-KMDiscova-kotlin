package com.example.kmd.data.mapper

import com.example.kmd.data.remote.dto.cart.CartDto
import com.example.kmd.data.remote.dto.cart.CartItemDto
import com.example.kmd.domain.model.Cart
import com.example.kmd.domain.model.CartItem
import com.example.kmd.domain.model.User
import com.example.kmd.domain.model.UserType

fun CartDto.toDomain(): Cart {
    val userObject = User(
        id = user,
        email = "",
        userType = UserType.Parent,
        isVerified = false
    )
    return Cart(
        cartId = cart_id,
        user = userObject,
        items = items.map { it.toDomain() },
        totalAmount = total_amount?.toDoubleOrNull() ?: 0.0
    )
}

fun CartItemDto.toDomain(): CartItem {
    return CartItem(
        itemId = item_id,
        child = child.toDomain(),
        psychologist = psychologist.toDomain(),
        sessionType = session_type,
        scheduledStartTime = scheduled_start_time,
        scheduledEndTime = scheduled_end_time,
        price = price.toDoubleOrNull() ?: 0.0,
        currency = currency
    )
}