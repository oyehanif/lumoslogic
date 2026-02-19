package com.hanif.lumoslogicpractical.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeys(remoteKeys: List<RemoteKeyEntity>)

    @Query("SELECT * FROM remote_keys WHERE id = :id")
    suspend fun getRemoteKeyById(id: Int): RemoteKeyEntity?

    @Query("DELETE FROM remote_keys")
    suspend fun clearKeys()
}
