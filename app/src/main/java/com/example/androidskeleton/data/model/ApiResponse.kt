package com.example.androidskeleton.data.model

data class ApiResponse<T>(
    val total_count: Int,
    val items: T
)