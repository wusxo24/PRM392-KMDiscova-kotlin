package com.example.kmd.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey val id: String,
    val productId: String,
    val productName: String,
    val quantity: Int,
    val price: Double,
    val imageUrl: String?
)
