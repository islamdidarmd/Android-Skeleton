package com.example.androidskeleton.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.androidskeleton.data.model.search.History

@Dao
interface HistoryDao: BaseDao<History> {
    @Query("select text from histories")
    fun getAll(): LiveData<List<String>>
}