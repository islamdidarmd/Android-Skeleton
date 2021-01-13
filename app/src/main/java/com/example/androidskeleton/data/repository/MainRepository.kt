package com.example.androidskeleton.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.androidskeleton.data.api.GitHubModule
import com.example.androidskeleton.data.db.AppDatabase
import com.example.androidskeleton.data.model.ApiResponse
import com.example.androidskeleton.data.model.search.History
import com.example.androidskeleton.data.model.search.Repo
import retrofit2.Retrofit

class MainRepository(
    private val db: AppDatabase,
    private val api: GitHubModule
) : BaseRepository() {

    suspend fun insertToDb(query: String) {
        db.historyDao().insert(
            History(text = query)
        )
    }

    suspend fun searchRepo(query: String): ApiResponse<List<Repo>>? {
        return try {
            api.getRepos(query).body()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getRecent(): LiveData<List<String>> {
        return db.historyDao().getAll()
    }
}