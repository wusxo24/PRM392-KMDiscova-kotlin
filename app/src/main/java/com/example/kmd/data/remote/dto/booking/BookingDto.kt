package com.example.kmd.data.remote.dto.booking

import com.google.gson.annotations.SerializedName

data class MyBookingsResponse(
    val count: Int,
    val appointments: List<BookingDto>
)

data class BookingDto(
    @SerializedName("appointment_id")
    val appointmentId: String,
    @SerializedName("child_name")
    val childName: String,
    @SerializedName("psychologist_name")
    val psychologistName: String,
    @SerializedName("session_type")
    val sessionType: String,
    @SerializedName("appointment_status")
    val appointmentStatus: String,
    @SerializedName("scheduled_start_time")
    val scheduledStartTime: String,
    @SerializedName("scheduled_end_time")
    val scheduledEndTime: String,
    @SerializedName("duration_hours")
    val durationHours: Int,
    @SerializedName("is_upcoming")
    val isUpcoming: Boolean,
    @SerializedName("meeting_address")
    val meetingAddress: String?
)