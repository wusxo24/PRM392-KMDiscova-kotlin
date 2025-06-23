package com.example.kmd.data.remote.dto.psychologist

data class PsychologistDto(
    val user: String,
    val full_name: String,
    val profile_picture_url: String?,
    val years_of_experience: Int,
    val biography: String?,
    val offers_initial_consultation: Boolean,
    val offers_online_sessions: Boolean,
    val services_offered: List<String>,
    val office_address: String?,
    val website_url: String?,
    val linkedin_url: String?,
    val hourly_rate: String?,
    val initial_consultation_rate: String?,
    val pricing: PricingDto?,
    val profile_completeness: String?,
    val license_issuing_authority: String?,
    val created_at: String
)

data class PsychologistListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PsychologistDto>
)
