package com.example.kmd.data.remote.dto.psychologist

data class CertificationDto(
    val name: String?,
    val year: String?,
    val institution: String?,
    val expiry_date: String? = null,
    val certification_id: String? = null
)
