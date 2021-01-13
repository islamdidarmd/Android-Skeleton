package com.example.androidskeleton.di

import androidx.room.Room
import com.example.androidskeleton.data.db.AppDatabase
import org.koin.dsl.module

val dbModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(get(), AppDatabase::class.java, "app.db")
            .fallbackToDestructiveMigration() //we want to destroy all the tables if migration fails
            .build()
    }
}