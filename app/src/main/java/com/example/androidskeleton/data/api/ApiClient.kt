package com.example.androidskeleton.data.api

import com.example.androidskeleton.util.BASE_URL
import com.example.androidskeleton.util.OKHTTP_CONNECT_TIMEOUT
import com.example.androidskeleton.util.OKHTTP_READ_TIMEOUT
import com.example.androidskeleton.util.OKHTTP_WRITE_TIMEOUT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private val okhttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(OKHTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(OKHTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .readTimeout(OKHTTP_READ_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }
    private val apiClient by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttpClient)
            .build()
    }

    @JvmStatic
    fun getClient(): Retrofit {
        return apiClient
    }

    inline fun <reified T> createNetworkCaller(): T {
        return getClient().create(T::class.java)
    }
}
