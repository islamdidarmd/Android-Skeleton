package com.example.androidskeleton.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.androidskeleton.data.api.GitHubModule
import com.example.androidskeleton.data.db.AppDatabase
import com.example.androidskeleton.data.model.ApiResponse
import com.example.androidskeleton.data.model.search.History
import com.example.androidskeleton.data.model.search.Repo

class MainRepository(val context: Context) {
    private val api: GitHubModule by lazy {
        GitHubModule.create()
    }
    private val db by lazy {
        AppDatabase.getInstance(context)
    }

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