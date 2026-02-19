package com.hanif.lumoslogicpractical.data.remote

import com.hanif.lumoslogicpractical.data.remote.dto.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DummyJsonApi {
    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): ProductResponse

    companion object {
        const val BASE_URL = "https://dummyjson.com/"
    }
}
