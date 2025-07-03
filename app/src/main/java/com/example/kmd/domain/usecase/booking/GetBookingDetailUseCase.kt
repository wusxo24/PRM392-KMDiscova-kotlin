package com.example.kmd.domain.usecase.booking

import com.example.kmd.domain.model.BookingDetail
import com.example.kmd.domain.repository.IBookingRepository
import javax.inject.Inject

class GetBookingDetailUseCase @Inject constructor(
    private val repository: IBookingRepository
) {
    suspend operator fun invoke(appointmentId: String): Result<BookingDetail> {
        return repository.getBookingDetail(appointmentId)
    }
}