package com.example.kmd.data.repository

import com.example.kmd.data.mapper.toDomain
import com.example.kmd.data.remote.api.BookingApiService
import com.example.kmd.domain.model.Booking
import com.example.kmd.domain.model.BookingDetail
import com.example.kmd.domain.repository.IBookingRepository
import javax.inject.Inject

class BookingRepository @Inject constructor(
    private val apiService: BookingApiService
) : IBookingRepository {
    override suspend fun getMyBookings(): Result<List<Booking>> {
        return try {
            val response = apiService.getMyBookings()
            Result.success(response.appointments.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Add this new implementation
    override suspend fun getBookingDetail(appointmentId: String): Result<BookingDetail> {
        return try {
            val response = apiService.getBookingDetail(appointmentId)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}