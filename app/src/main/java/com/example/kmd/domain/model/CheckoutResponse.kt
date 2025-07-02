package com.example.kmd.domain.model

import com.google.gson.annotations.SerializedName

data class CheckoutResponse(
    val message: String,
    val order: OrderDetails,
)

data class OrderDetails(
    @SerializedName("order_id")
    val id: String,
    val amount: String,
    val status: String
)
