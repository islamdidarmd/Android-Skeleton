package com.example.androidskeleton.data.model.search

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "histories", indices = [Index(value = ["text"], unique = true)])
data class History(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val text: String
)