package com.example.kmd.data.mapper

import com.example.kmd.data.remote.dto.psychologist.PricingDto
import com.example.kmd.data.remote.dto.psychologist.PsychologistDetailDto
import com.example.kmd.data.remote.dto.psychologist.PsychologistDto
import com.example.kmd.domain.model.Psychologist
import com.example.kmd.domain.model.PsychologistDetail
import com.example.kmd.domain.model.Pricing
fun PsychologistDto.toDomain(): Psychologist {
    return Psychologist(
        id = user,
        fullName = full_name,
        profilePictureUrl = profile_picture_url,
        yearsOfExperience = years_of_experience,
        biography = biography,
        pricing = pricing?.toDomain(), // ✅ CORRECT
        offersInitialConsultation = offers_initial_consultation,
        offersOnlineSessions = offers_online_sessions

    )
}

fun PsychologistDetailDto.toDomain(): PsychologistDetail {
    return PsychologistDetail(
        id = user.id,
        fullName = full_name,
        email = email,
        profilePictureUrl = profile_picture_url,
        yearsOfExperience = years_of_experience,
        biography = biography,
        licenseAuthority = license_issuing_authority,
        hourlyRate = hourly_rate,
        initialConsultationRate = initial_consultation_rate,
        services = services_offered
    )
}


fun PricingDto.toDomain(): Pricing {
    return Pricing(
        onlineRate = online_session_rate,
        initialRate = initial_consultation_rate,
        currency = currency
    )
}

