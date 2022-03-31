package com.project.tracker_data.remote

import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.logging.Level

class TrackerRetrofit {

    fun createHttp():OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            ).build()
    }

    fun createService():Retrofit{
        return Retrofit.Builder()
            .client(createHttp())
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(FoodAPI.BASE_URL)
            .build()
    }

}