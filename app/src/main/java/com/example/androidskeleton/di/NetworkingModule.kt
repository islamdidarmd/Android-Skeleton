package com.example.androidskeleton.di

import com.example.androidskeleton.data.api.GitHubModule
import com.example.androidskeleton.util.BASE_URL
import com.example.androidskeleton.util.OKHTTP_CONNECT_TIMEOUT
import com.example.androidskeleton.util.OKHTTP_READ_TIMEOUT
import com.example.androidskeleton.util.OKHTTP_WRITE_TIMEOUT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkingModule = module {
    factory<OkHttpClient> {
        OkHttpClient.Builder()
            .connectTimeout(OKHTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(OKHTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .readTimeout(OKHTTP_READ_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }
}

inline fun <reified T> Retrofit.create(): T {
    return create(T::class.java)
}