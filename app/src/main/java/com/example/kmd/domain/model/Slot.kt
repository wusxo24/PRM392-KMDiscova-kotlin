package com.example.kmd.domain.model

data class AvailableSlotsResponse(
    val psychologistName: String,
    val sessionType: String,
    val totalSlots: Int,
    val availableSlots: List<Slot>
)

data class Slot(
    val id: Int,
    val date: String,
    val startTime: String,
    val endTime: String,
    val sessionTypes: List<String>,
    val isConsecutiveBlock: Boolean
)
