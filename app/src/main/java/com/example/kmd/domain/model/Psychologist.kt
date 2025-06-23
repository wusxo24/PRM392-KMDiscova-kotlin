package com.example.kmd.domain.model

data class Psychologist(
    val id: String,
    val fullName: String,
    val profilePictureUrl: String?,
    val yearsOfExperience: Int,
    val biography: String?,
    val pricing: Pricing?
)

data class PsychologistDetail(
    val id: String,
    val fullName: String,
    val email: String,
    val profilePictureUrl: String?,
    val yearsOfExperience: Int,
    val biography: String?,
    val licenseAuthority: String?,
    val hourlyRate: String?,
    val initialConsultationRate: String?,
    val services: List<String>
)
