package com.example.kmd.domain.model

data class BookingDetail(
    val appointmentId: String,
    val childName: String,
    val psychologistName: String,
    val psychologistEmail: String,
    val parentName: String,
    val parentEmail: String,
    val sessionType: String,
    val appointmentStatus: String,
    val scheduledStartTime: String,
    val scheduledEndTime: String,
    val meetingLink: String?,
    val parentNotes: String?,
    val durationHours: Int,
    val canBeCancelled: Boolean
)