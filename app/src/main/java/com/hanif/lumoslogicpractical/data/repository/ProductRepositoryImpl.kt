package com.hanif.lumoslogicpractical.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.hanif.lumoslogicpractical.data.local.AppDatabase
import com.hanif.lumoslogicpractical.data.local.ProductDao
import com.hanif.lumoslogicpractical.data.mapper.toDomain
import com.hanif.lumoslogicpractical.data.paging.ProductRemoteMediator
import com.hanif.lumoslogicpractical.data.remote.DummyJsonApi
import com.hanif.lumoslogicpractical.domain.model.Product
import com.hanif.lumoslogicpractical.domain.repository.ProductRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: DummyJsonApi,
    private val database: AppDatabase
) : ProductRepository {

    private val dao = database.productDao()

    @OptIn(ExperimentalPagingApi::class)
    override fun getProducts(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                enablePlaceholders = false
            ),
            remoteMediator = ProductRemoteMediator(database, api),
            pagingSourceFactory = { dao.getAllProducts() }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override suspend fun getProductById(id: Int): Product? {
        return dao.getProductById(id)?.toDomain()
    }
}
