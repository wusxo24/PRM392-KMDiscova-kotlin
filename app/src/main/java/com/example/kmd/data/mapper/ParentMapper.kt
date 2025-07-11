package com.example.kmd.data.mapper

import com.example.kmd.data.remote.dto.child.ChildDto
import com.example.kmd.data.remote.dto.parent.ParentProfileDto
import com.example.kmd.domain.model.Child
import com.example.kmd.domain.model.ParentProfile

fun ParentProfileDto.toDomain(): ParentProfile {
    return ParentProfile(
        userId = user_id ?: "",
        firstName = first_name ?: "",
        lastName = last_name ?: "",
        fullName = full_name ?: "",
        phoneNumber = phone_number ?: "",
        addressLine1 = address_line1 ?: "",
        city = city ?: "",
        stateProvince = state_province ?: "",
        postalCode = postal_code ?: "",
        country = country ?: ""
    )
}

fun ChildDto.toDomain(): Child {
    return Child(
        id = id,
        firstName = first_name,
        lastName = last_name,
        displayName = display_name,
        dateOfBirth = date_of_birth,
        age = age,
        gender = gender
    )
}