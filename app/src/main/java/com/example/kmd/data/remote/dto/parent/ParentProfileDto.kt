package com.example.kmd.data.remote.dto.parent

data class ParentProfileDto(
    val user_id: String?,
    val first_name: String?,
    val last_name: String?,
    val full_name: String?,
    val phone_number: String?,
    val address_line1: String?,
    val city: String?,
    val state_province: String?,
    val postal_code: String?,
    val country: String?
)

data class CreateParentProfileRequest(
    val first_name: String,
    val last_name: String,
    val phone_number: String,
    val address_line1: String,
    val city: String,
    val state_province: String,
    val postal_code: String,
    val country: String
)