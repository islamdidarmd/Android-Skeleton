package com.example.androidskeleton.di

import com.example.androidskeleton.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UrlModule {

    @Provides
    @RetrofitBaseUrl
    fun providesBaseUrl(): String {
        return "http://localhost/"
    }
}