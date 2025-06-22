package com.example.kmd.di

import com.example.kmd.data.repository.AuthRepository
import com.example.kmd.data.remote.api.AuthApiService
import com.example.kmd.data.mapper.AuthMapper
import com.example.kmd.domain.repository.IAuthRepository
import com.example.kmd.domain.usecase.auth.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        apiService: AuthApiService,
        authMapper: AuthMapper
    ): IAuthRepository {
        return AuthRepository(apiService, authMapper)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(repo: IAuthRepository): LoginUseCase = LoginUseCase(repo)

    @Provides
    @Singleton
    fun provideRegisterUseCase(repo: IAuthRepository): RegisterUseCase = RegisterUseCase(repo)
}
