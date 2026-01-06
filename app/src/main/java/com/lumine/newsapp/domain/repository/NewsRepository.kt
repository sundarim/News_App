package com.lumine.newsapp.domain.repository

import com.lumine.newsapp.domain.model.NewsArticle
import com.lumine.newsapp.domain.util.Resource

interface NewsRepository {
    suspend fun getNewsArticles(
        page: Int,
        query: String
    ): Resource<Pair<List<NewsArticle>, Int>>

}