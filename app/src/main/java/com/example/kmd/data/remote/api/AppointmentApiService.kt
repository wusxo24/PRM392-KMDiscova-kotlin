package com.example.kmd.data.remote.api

import com.example.kmd.data.mapper.toDomain
import com.example.kmd.data.remote.dto.psychologist.AvailableSlotsResponseDto
import com.example.kmd.domain.model.AvailableSlotsResponse
import com.example.kmd.data.repository.AppointmentRepository
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

interface AppointmentApiService {
    @GET("/api/appointments/slots/available_for_booking/")
    suspend fun getAvailableSlots(
        @Query("psychologist_id") userId: String,
        @Query("session_type") sessionType: String
    ): AvailableSlotsResponseDto
}

class AppointmentRepositoryImpl @Inject constructor(
    private val api: AppointmentApiService
) : AppointmentRepository {
    override suspend fun getAvailableSlots(userId: String, sessionType: String): AvailableSlotsResponse {
        return api.getAvailableSlots(userId, sessionType).toDomain()
    }
}
