package com.example.kmd.data.remote.repository

import com.example.kmd.data.local.dao.CartDao
import com.example.kmd.data.remote.mapper.CartMapper
import com.example.kmd.domain.model.CartItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CartRepository(private val cartDao: CartDao) {
    suspend fun getCartItems(): List<CartItem> = withContext(Dispatchers.IO) {
        cartDao.getAllCartItems().map { CartMapper.entityToDomain(it) }
    }

    suspend fun addCartItem(item: CartItem) = withContext(Dispatchers.IO) {
        cartDao.insertCartItem(CartMapper.domainToEntity(item))
    }

    suspend fun updateCartItem(item: CartItem) = withContext(Dispatchers.IO) {
        cartDao.updateCartItem(CartMapper.domainToEntity(item))
    }

    suspend fun removeCartItem(item: CartItem) = withContext(Dispatchers.IO) {
        cartDao.deleteCartItem(CartMapper.domainToEntity(item))
    }

    suspend fun clearCart() = withContext(Dispatchers.IO) {
        cartDao.clearCart()
    }
}
