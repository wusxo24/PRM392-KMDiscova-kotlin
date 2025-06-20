package com.example.kmd.data.remote.mapper

import com.example.kmd.data.local.entities.CartItemEntity
import com.example.kmd.domain.model.CartItem

object CartMapper {
    fun entityToDomain(entity: CartItemEntity): CartItem =
        CartItem(
            id = entity.id,
            productId = entity.productId,
            productName = entity.productName,
            quantity = entity.quantity,
            price = entity.price,
            imageUrl = entity.imageUrl
        )

    fun domainToEntity(domain: CartItem): CartItemEntity =
        CartItemEntity(
            id = domain.id,
            productId = domain.productId,
            productName = domain.productName,
            quantity = domain.quantity,
            price = domain.price,
            imageUrl = domain.imageUrl
        )
}
