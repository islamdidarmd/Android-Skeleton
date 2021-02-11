package com.example.androidskeleton.di

import com.example.androidskeleton.data.api.GitHubModule
import com.example.androidskeleton.util.BASE_URL
import com.example.androidskeleton.util.OKHTTP_CONNECT_TIMEOUT
import com.example.androidskeleton.util.OKHTTP_READ_TIMEOUT
import com.example.androidskeleton.util.OKHTTP_WRITE_TIMEOUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class RetrofitBaseUrl

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Provides
    fun provideOkhttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(OKHTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(OKHTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .readTimeout(OKHTTP_READ_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, @RetrofitBaseUrl baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object UrlModule{
    @Provides
    @RetrofitBaseUrl
    fun providesBaseUrl(): String {
        return BASE_URL
    }
}