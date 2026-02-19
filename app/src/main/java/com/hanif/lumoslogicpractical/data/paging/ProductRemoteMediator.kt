package com.hanif.lumoslogicpractical.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.hanif.lumoslogicpractical.data.local.AppDatabase
import com.hanif.lumoslogicpractical.data.local.ProductEntity
import com.hanif.lumoslogicpractical.data.local.RemoteKeyEntity
import com.hanif.lumoslogicpractical.data.mapper.toEntity
import com.hanif.lumoslogicpractical.data.remote.DummyJsonApi
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

@OptIn(ExperimentalPagingApi::class)
class ProductRemoteMediator(
    private val database: AppDatabase,
    private val api: DummyJsonApi
) : RemoteMediator<Int, ProductEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProductEntity>
    ): MediatorResult {
        return try {
            val skip = when (loadType) {
                LoadType.REFRESH -> {
                    // Added a small delay to make the loading indicator visible during first launch
                    delay(1000) 
                    0
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.remoteKeyDao().getRemoteKeyById(1)
                    if (remoteKey?.nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    // Added delay for pagination call as well
                    delay(1000)
                    remoteKey.nextKey
                }
            }

            val response = api.getProducts(
                limit = state.config.pageSize,
                skip = skip
            )

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.productDao().clearAll()
                    database.remoteKeyDao().clearKeys()
                }

                val nextKey = skip + response.products.size
                database.remoteKeyDao().insertKeys(listOf(RemoteKeyEntity(1, nextKey)))
                database.productDao().insertProducts(response.products.map { it.toEntity() })
            }

            MediatorResult.Success(
                endOfPaginationReached = response.products.size < state.config.pageSize
            )
        } catch (e: UnknownHostException) {
            MediatorResult.Error(Exception("No internet connection. Please check your network."))
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
