package com.hanif.lumoslogicpractical.di

import android.content.Context
import androidx.room.Room
import com.hanif.lumoslogicpractical.data.local.AppDatabase
import com.hanif.lumoslogicpractical.data.local.ProductDao
import com.hanif.lumoslogicpractical.data.local.RemoteKeyDao
import com.hanif.lumoslogicpractical.data.remote.DummyJsonApi
import com.hanif.lumoslogicpractical.data.repository.ProductRepositoryImpl
import com.hanif.lumoslogicpractical.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDummyJsonApi(): DummyJsonApi {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(DummyJsonApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(DummyJsonApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "lumos_db"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    fun provideRemoteKeyDao(database: AppDatabase): RemoteKeyDao {
        return database.remoteKeyDao()
    }

    @Provides
    @Singleton
    fun provideProductRepository(api: DummyJsonApi, database: AppDatabase): ProductRepository {
        return ProductRepositoryImpl(api, database)
    }
}
