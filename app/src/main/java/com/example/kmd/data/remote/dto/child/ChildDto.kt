package com.example.kmd.data.remote.dto.child

data class ChildDto(
    val id: String,
    val first_name: String,
    val last_name: String,
    val nickname: String,
    val display_name: String,
    val date_of_birth: String,
    val age: Int,
    val gender: String,
    val health_status: String?,
    val developmental_concerns: String?,
    val parental_goals: String?,
    val created_at: String?
)

data class AddChildRequest(
    val first_name: String,
    val date_of_birth: String,
    val gender: String,
    val last_name: String? = null,
    val nickname: String? = null,
    val health_status: String? = null,
    val developmental_concerns: String? = null,
    val parental_goals: String? = null
)

data class MyChildrenResponse(
    val count: Int,
    val children: List<ChildDto>
)

data class CreateChildResponse(
    val message: String,
    val child: ChildDto
)