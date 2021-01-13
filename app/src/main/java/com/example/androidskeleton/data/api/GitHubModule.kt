package com.example.androidskeleton.data.api

import com.example.androidskeleton.data.model.ApiResponse
import com.example.androidskeleton.data.model.search.Repo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubModule {
    @GET("search/repositories")
    suspend fun getRepos(@Query("q") query: String): Response<ApiResponse<List<Repo>>?>
}