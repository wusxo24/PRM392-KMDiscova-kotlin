package com.example.kmd.domain.usecase.booking

import com.example.kmd.domain.model.Booking
import com.example.kmd.domain.repository.IBookingRepository
import javax.inject.Inject

class GetMyBookingsUseCase @Inject constructor(
    private val repository: IBookingRepository
) {
    suspend operator fun invoke(): Result<List<Booking>> {
        return repository.getMyBookings()
    }
}