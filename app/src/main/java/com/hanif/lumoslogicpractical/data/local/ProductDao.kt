package com.hanif.lumoslogicpractical.data.local

import androidx.paging.PagingSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAllProducts(): PagingSource<Int, ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Query("DELETE FROM products")
    suspend fun clearAll()

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: Int): ProductEntity?
}
