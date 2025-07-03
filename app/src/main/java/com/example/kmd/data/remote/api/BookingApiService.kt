package com.example.kmd.data.remote.api

import com.example.kmd.data.remote.dto.booking.BookingDetailDto
import com.example.kmd.data.remote.dto.booking.MyBookingsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BookingApiService {
    @GET("/api/appointments/my_appointments/")
    suspend fun getMyBookings(): MyBookingsResponse

    // Add this new function
    @GET("/api/appointments/{appointment_id}/")
    suspend fun getBookingDetail(@Path("appointment_id") appointmentId: String): BookingDetailDto
}