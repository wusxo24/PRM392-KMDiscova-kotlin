package com.example.kmd.di
import com.example.kmd.domain.usecase.booking.GetBookingDetailUseCase
import com.example.kmd.data.remote.api.BookingApiService
import com.example.kmd.data.repository.BookingRepository
import com.example.kmd.domain.repository.IBookingRepository
import com.example.kmd.domain.usecase.booking.GetMyBookingsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BookingModule {

    @Provides
    @Singleton
    fun provideBookingApiService(retrofit: Retrofit): BookingApiService {
        return retrofit.create(BookingApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideBookingRepository(apiService: BookingApiService): IBookingRepository {
        return BookingRepository(apiService)
    }

    @Provides
    fun provideGetMyBookingsUseCase(repository: IBookingRepository): GetMyBookingsUseCase {
        return GetMyBookingsUseCase(repository)
    }

    // Add this new provider
    @Provides
    fun provideGetBookingDetailUseCase(repository: IBookingRepository): GetBookingDetailUseCase {
        return GetBookingDetailUseCase(repository)
    }
}