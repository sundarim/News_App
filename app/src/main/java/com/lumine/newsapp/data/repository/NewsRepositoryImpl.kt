package com.lumine.newsapp.data.repository

import com.lumine.newsapp.data.mapper.toArticles
import com.lumine.newsapp.data.remote.api.NewsApi
import com.lumine.newsapp.domain.model.NewsArticle
import com.lumine.newsapp.domain.repository.NewsRepository
import com.lumine.newsapp.util.Constants
import com.lumine.newsapp.domain.util.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi
) : NewsRepository {

    override suspend fun getNewsArticles(page: Int, query: String):
            Resource<Pair<List<NewsArticle>, Int>> {
        return try {
            val response = api.getNews(
                query = query,
                page = page,
                pageSize = Constants.ITEMS_PER_PAGE,
                sortBy = "publishedAt"
            )

            val articles = response.articles
                .filterNot { it.title.isNullOrBlank() || it.url.isNullOrBlank() }
                .distinctBy { it.url }
                .toArticles()

            Resource.Success(articles to response.totalResults)

        } catch (e: HttpException) {
            Resource.Error("Network error: ${e.message}")
        } catch (e: IOException) {
            Resource.Error("No internet connection")
        } catch (e: Exception) {
            Resource.Error("Unexpected error: ${e.message}")
        }
    }


}