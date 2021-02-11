package com.example.androidskeleton.data.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideGitHubModule(retrofit: Retrofit): GitHubModule {
        return retrofit.create(GitHubModule::class.java)
    }
}