package com.example.kmd.domain.repository

import com.example.kmd.domain.model.Cart

interface ICartRepository {
    suspend fun getCart(): Result<Cart>
}