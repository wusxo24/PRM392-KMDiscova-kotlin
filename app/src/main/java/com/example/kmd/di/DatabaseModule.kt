package com.example.kmd.di

import android.content.Context
import androidx.room.Room
import com.example.kmd.data.local.database.KmDDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): KmDDatabase {
        return Room.databaseBuilder(
            context,
            KmDDatabase::class.java,
            "kmd_database"
        ).build()
    }
}

