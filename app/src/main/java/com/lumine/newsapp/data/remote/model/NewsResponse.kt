package com.lumine.newsapp.data.remote.model

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsArticleDTO>
)