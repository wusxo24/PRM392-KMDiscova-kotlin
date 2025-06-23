package com.example.kmd.di

import com.example.kmd.data.remote.api.PsychologistApiService
import com.example.kmd.domain.repository.IPsychologistRepository
import com.example.kmd.domain.repository.PsychologistRepository
import com.example.kmd.domain.usecase.psychologist.GetPsychologistsUseCase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PsychologistModule {

    @Provides
    @Singleton
    fun providePsychologistApiService(retrofit: Retrofit): PsychologistApiService {
        return retrofit.create(PsychologistApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePsychologistRepository(
        api: PsychologistApiService
    ): IPsychologistRepository {
        return PsychologistRepository(api)
    }

    @Provides
    fun provideGetPsychologistsUseCase(
        repository: IPsychologistRepository
    ): GetPsychologistsUseCase {
        return GetPsychologistsUseCase(repository)
    }

}
