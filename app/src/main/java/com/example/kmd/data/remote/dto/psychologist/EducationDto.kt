package com.example.kmd.data.remote.dto.psychologist

data class EducationDto(
    val year: String?,
    val degree: String?,
    val institution: String?,
    val honors: String? = null,
    val field_of_study: String? = null
)
