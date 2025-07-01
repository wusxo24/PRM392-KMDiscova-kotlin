package com.example.kmd.di

import com.example.kmd.data.remote.api.CartApiService
import com.example.kmd.data.remote.api.ParentApiService
import com.example.kmd.data.repository.CartRepository
import com.example.kmd.data.repository.ParentRepository
import com.example.kmd.domain.repository.ICartRepository
import com.example.kmd.domain.repository.IParentRepository
import com.example.kmd.domain.usecase.cart.GetCartUseCase
import com.example.kmd.domain.usecase.parent.GetParentProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ParentModule {

    @Provides
    @Singleton
    fun provideParentApiService(retrofit: Retrofit): ParentApiService {
        return retrofit.create(ParentApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideParentRepository(apiService: ParentApiService): IParentRepository {
        return ParentRepository(apiService)
    }

    @Provides
    fun provideGetParentProfileUseCase(repository: IParentRepository): GetParentProfileUseCase {
        return GetParentProfileUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCartApiService(retrofit: Retrofit): CartApiService {
        return retrofit.create(CartApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCartRepository(apiService: CartApiService): ICartRepository {
        return CartRepository(apiService)
    }

    @Provides
    fun provideGetCartUseCase(repository: ICartRepository): GetCartUseCase {
        return GetCartUseCase(repository)
    }
}