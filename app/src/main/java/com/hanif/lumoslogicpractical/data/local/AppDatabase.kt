package com.hanif.lumoslogicpractical.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ProductEntity::class, RemoteKeyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}
