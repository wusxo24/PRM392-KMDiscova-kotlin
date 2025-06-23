package com.example.kmd.di

import com.example.kmd.data.local.preferences.PreferencesManager
import com.example.kmd.data.remote.api.AuthApiService
import com.example.kmd.data.repository.AuthRepository
import com.example.kmd.domain.repository.IAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthRepositoryModule {

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        authApiService: AuthApiService,
        preferencesManager: PreferencesManager
    ): IAuthRepository {
        return AuthRepository(authApiService, preferencesManager)
    }
}