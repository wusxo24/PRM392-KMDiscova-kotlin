package com.example.kmd.domain.model

data class CheckoutResponse(
    val message: String,
    val order: OrderDetails,
    val paymentUrl: String?
)

data class OrderDetails(
    val id: String,
    val amount: String,
    val status: String
)
