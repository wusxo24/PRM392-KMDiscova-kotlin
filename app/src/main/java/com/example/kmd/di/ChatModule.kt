package com.example.kmd.di

import com.example.kmd.data.remote.api.ChatApiService
import com.example.kmd.data.repository.ChatRepository
import com.example.kmd.domain.repository.IChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatModule {

    @Provides
    @Singleton
    fun provideChatApiService(retrofit: Retrofit): ChatApiService {
        return retrofit.create(ChatApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideChatRepository(api: ChatApiService): IChatRepository {
        return ChatRepository(api)
    }
} 