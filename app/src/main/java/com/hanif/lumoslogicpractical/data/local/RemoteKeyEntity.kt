package com.hanif.lumoslogicpractical.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey val id: Int,
    val nextKey: Int?
)
