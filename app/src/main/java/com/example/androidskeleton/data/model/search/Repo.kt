package com.example.androidskeleton.data.model.search

data class Repo(
    val full_name: String,
    val description: String,
    val owner: Owner,
    val html_url: String
)

data class Owner(
    val id: Int,
    val avatar_url: String,
    val html_url: String
)