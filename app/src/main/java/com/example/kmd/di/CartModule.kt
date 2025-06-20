package com.example.kmd.di

import android.content.Context
import com.example.kmd.data.local.dao.CartDao
import com.example.kmd.data.remote.repository.CartRepository
import com.example.kmd.domain.usecase.cart.AddToCartUseCase
import com.example.kmd.domain.usecase.cart.ClearCartUseCase
import com.example.kmd.domain.usecase.cart.GetCartItemsUseCase
import com.example.kmd.domain.usecase.cart.RemoveFromCartUseCase
import com.example.kmd.domain.usecase.cart.UpdateCartItemUseCase
import com.example.kmd.data.local.database.KmDDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CartModule {
    @Provides
    @Singleton
    fun provideCartDao(db: KmDDatabase): CartDao = db.cartDao()

    @Provides
    @Singleton
    fun provideCartRepository(cartDao: CartDao): CartRepository = CartRepository(cartDao)

    @Provides
    @Singleton
    fun provideAddToCartUseCase(repository: CartRepository) = AddToCartUseCase(repository)

    @Provides
    @Singleton
    fun provideGetCartItemsUseCase(repository: CartRepository) = GetCartItemsUseCase(repository)

    @Provides
    @Singleton
    fun provideUpdateCartItemUseCase(repository: CartRepository) = UpdateCartItemUseCase(repository)

    @Provides
    @Singleton
    fun provideRemoveFromCartUseCase(repository: CartRepository) = RemoveFromCartUseCase(repository)

    @Provides
    @Singleton
    fun provideClearCartUseCase(repository: CartRepository) = ClearCartUseCase(repository)
}
