package com.example.androidskeleton

import com.example.androidskeleton.di.networkingModule
import com.example.androidskeleton.di.appModule
import com.example.androidskeleton.di.dbModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Application : android.app.Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            androidLogger()
            modules(
                listOf(
                    networkingModule,
                    appModule,
                    dbModule
                )
            )
        }
    }
}