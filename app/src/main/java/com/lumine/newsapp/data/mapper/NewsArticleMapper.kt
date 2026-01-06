package com.lumine.newsapp.data.mapper

import com.lumine.newsapp.data.remote.model.NewsArticleDTO
import com.lumine.newsapp.domain.model.NewsArticle

fun NewsArticleDTO.toArticle(): NewsArticle {
    return NewsArticle(
        url = url ?: "",
        author = author,
        title = title ?: "No Title",
        description = description,
        urlToImage = urlToImage,
        publishedAt = publishedAt ?: "",
        content = content,
        sourceName = source?.name
    )
}

fun List<NewsArticleDTO>.toArticles(): List<NewsArticle> {
    return this.map { it.toArticle() }
}