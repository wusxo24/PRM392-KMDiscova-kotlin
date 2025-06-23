package com.example.kmd.di

import com.example.kmd.domain.repository.IAuthRepository
import com.example.kmd.domain.usecase.auth.LoginUseCase
import com.example.kmd.domain.usecase.auth.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideLoginUseCase(authRepository: IAuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Provides
    fun provideRegisterUseCase(authRepository: IAuthRepository): RegisterUseCase {
        return RegisterUseCase(authRepository)
    }

}