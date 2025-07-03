package com.example.kmd.domain.model

data class Booking(
    val appointmentId: String,
    val childName: String,
    val psychologistName: String,
    val sessionType: String,
    val appointmentStatus: String,
    val scheduledStartTime: String,
    val scheduledEndTime: String,
    val durationHours: Int,
    val isUpcoming: Boolean,
    val meetingAddress: String?
)