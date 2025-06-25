package com.example.kmd.data.repository

import com.example.kmd.domain.model.AvailableSlotsResponse

interface AppointmentRepository {
    suspend fun getAvailableSlots(userId: String, sessionType: String): AvailableSlotsResponse
}
