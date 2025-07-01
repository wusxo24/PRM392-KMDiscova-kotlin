package com.example.kmd.domain.model

data class ParentProfile(
    val userId: String,
    val firstName: String,
    val lastName: String,
    val fullName: String,
    val phoneNumber: String,
    val addressLine1: String,
    val city: String,
    val stateProvince: String,
    val postalCode: String,
    val country: String
)