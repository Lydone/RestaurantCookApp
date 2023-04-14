package com.lydone.restaurantcookapp.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.lydone.restaurantcookapp.data.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    fun provideApiService(): ApiService = Retrofit.Builder()
        .baseUrl("http://192.168.3.5:8080/api/cook/")
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build()
        )
        .build()
        .create(ApiService::class.java)
}