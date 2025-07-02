package com.example.kmd.domain.model

data class InitiatePaymentRequest(
    val success_url: String,
    val cancel_url: String,
    val provider: String
)


data class PaymentInitiationResponse(
    val message: String,
    val payment_data: PaymentData,
    val paymentUrl: String
)

data class PaymentData(
    val client_secret: String,
    val payment_intent_id: String,
    val payment_method: String,
    val currency: String
)

