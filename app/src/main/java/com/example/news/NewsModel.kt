package com.example.news

data class NewsModel(
    val titel: String,
    val description: String,
    val image_url: String,
    val news_link: String,
    val date: String,
    val category: String,
    val language: String
)