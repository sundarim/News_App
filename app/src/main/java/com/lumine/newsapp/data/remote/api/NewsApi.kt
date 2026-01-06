package com.lumine.newsapp.data.remote.api

import com.lumine.newsapp.data.remote.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything")
    suspend fun getNews(
        @Query("q") query: String ,
        @Query("page") page: Int ,
        @Query("pageSize") pageSize: Int,
        @Query("sortBy") sortBy: String ,
    ): NewsResponse
}