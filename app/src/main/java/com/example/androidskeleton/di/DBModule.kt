package com.example.androidskeleton.di

import android.content.Context
import androidx.room.Room
import com.example.androidskeleton.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "app.db")
            .fallbackToDestructiveMigration() //we want to destroy all the tables if migration fails
            .build()
    }
}