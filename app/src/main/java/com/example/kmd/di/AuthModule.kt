package com.example.kmd.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // This can be empty for now, or contain app-wide configurations
    // Remove any AuthModule declarations from here
}