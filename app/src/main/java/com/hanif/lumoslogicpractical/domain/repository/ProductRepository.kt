package com.hanif.lumoslogicpractical.domain.repository

import androidx.paging.PagingData
import com.hanif.lumoslogicpractical.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<PagingData<Product>>
    suspend fun getProductById(id: Int): Product?
}
