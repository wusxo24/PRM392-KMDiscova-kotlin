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
    val health_status: String,
    val developmental_concerns: String,
    val parental_goals: String,
    val created_at: String
)

data class AddChildRequest(
    val first_name: String,
    val last_name: String,
    val nickname: String,
    val date_of_birth: String,
    val gender: String,
    val health_status: String,
    val developmental_concerns: String,
    val parental_goals: String
)

data class MyChildrenResponse(
    val count: Int,
    val children: List<ChildDto>
)