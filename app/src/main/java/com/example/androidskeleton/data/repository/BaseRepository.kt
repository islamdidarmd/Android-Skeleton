package com.example.androidskeleton.data.repository

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Retrofit

open class BaseRepository {
    protected inline fun <reified T> Retrofit.createApi(): T {
        return create(T::class.java)
    }
}