package com.example.kmd.data.remote.dto.booking

import com.example.kmd.data.remote.dto.child.ChildDto
import com.google.gson.annotations.SerializedName

data class BookingDetailDto(
    @SerializedName("appointment_id") val appointmentId: String,
    val child: ChildDto,
    @SerializedName("psychologist") val psychologist: PsychologistInfoInBookingDto,
    val parent: ParentInfoInBookingDto,
    @SerializedName("session_type") val sessionType: String,
    @SerializedName("appointment_status") val appointmentStatus: String,
    @SerializedName("scheduled_start_time") val scheduledStartTime: String,
    @SerializedName("scheduled_end_time") val scheduledEndTime: String,
    @SerializedName("actual_start_time") val actualStartTime: String?,
    @SerializedName("actual_end_time") val actualEndTime: String?,
    @SerializedName("meeting_address") val meetingAddress: String?,
    @SerializedName("meeting_link") val meetingLink: String?,
    @SerializedName("parent_notes") val parentNotes: String?,
    @SerializedName("psychologist_notes") val psychologistNotes: String?,
    @SerializedName("duration_hours") val durationHours: Int,
    @SerializedName("is_upcoming") val isUpcoming: Boolean,
    @SerializedName("can_be_cancelled") val canBeCancelled: Boolean
)

data class PsychologistInfoInBookingDto(
    val user: String,
    val email: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("profile_picture_url") val profilePictureUrl: String?,
    @SerializedName("years_of_experience") val yearsOfExperience: Int
)

data class ParentInfoInBookingDto(
    val user: String,
    val email: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("profile_picture_url") val profilePictureUrl: String?
)