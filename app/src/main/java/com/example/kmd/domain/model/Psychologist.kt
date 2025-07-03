package com.example.kmd.domain.model

import com.example.kmd.data.remote.dto.auth.UserDto


data class Psychologist(
    val id: String,
    val fullName: String,
    val profilePictureUrl: String?,
    val yearsOfExperience: Int,
    val biography: String?,
    val pricing: Pricing?,
    val offersInitialConsultation: Boolean,
    val offersOnlineSessions: Boolean
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
    val services: List<String>,
    val offersInitialConsultation: Boolean,
    val offersOnlineSessions: Boolean,
    val user: UserDto,
    val officeAddress: String?
)
