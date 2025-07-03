package com.example.kmd.domain.repository

import com.example.kmd.domain.model.Booking
import com.example.kmd.domain.model.BookingDetail

interface IBookingRepository {
    suspend fun getMyBookings(): Result<List<Booking>>

    // Add this new function
    suspend fun getBookingDetail(appointmentId: String): Result<BookingDetail>
}