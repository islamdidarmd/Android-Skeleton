package com.example.androidskeleton.di

import com.example.androidskeleton.data.api.GitHubModule
import com.example.androidskeleton.data.repository.MainRepository
import com.example.androidskeleton.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    factory<MainRepository> {
        val retrofit: Retrofit = get()
        return@factory MainRepository(get(), retrofit.create(GitHubModule::class.java))
    }

    viewModel<MainViewModel> {
        MainViewModel(get())
    }
}