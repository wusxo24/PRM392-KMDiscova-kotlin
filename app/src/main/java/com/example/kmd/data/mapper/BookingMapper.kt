package com.example.kmd.data.mapper

import com.example.kmd.data.remote.dto.booking.BookingDetailDto
import com.example.kmd.data.remote.dto.booking.BookingDto
import com.example.kmd.domain.model.Booking
import com.example.kmd.domain.model.BookingDetail

// This function was added previously
fun BookingDto.toDomain(): Booking {
    return Booking(
        appointmentId = appointmentId,
        childName = childName,
        psychologistName = psychologistName,
        sessionType = sessionType,
        appointmentStatus = appointmentStatus,
        scheduledStartTime = scheduledStartTime,
        scheduledEndTime = scheduledEndTime,
        durationHours = durationHours,
        isUpcoming = isUpcoming,
        meetingAddress = meetingAddress
    )
}

// Add this new mapping function for the details
fun BookingDetailDto.toDomain(): BookingDetail {
    return BookingDetail(
        appointmentId = appointmentId,
        childName = child.display_name,
        psychologistName = psychologist.fullName,
        psychologistEmail = psychologist.email,
        parentName = parent.fullName,
        parentEmail = parent.email,
        sessionType = sessionType,
        appointmentStatus = appointmentStatus,
        scheduledStartTime = scheduledStartTime,
        scheduledEndTime = scheduledEndTime,
        meetingLink = meetingLink,
        parentNotes = parentNotes,
        durationHours = durationHours,
        canBeCancelled = canBeCancelled
    )
}