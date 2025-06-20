package com.example.kmd.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kmd.data.local.dao.CartDao
import com.example.kmd.data.local.entities.CartItemEntity

@Database(
    entities = [CartItemEntity::class],
    version = 1
)
abstract class KmDDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}

