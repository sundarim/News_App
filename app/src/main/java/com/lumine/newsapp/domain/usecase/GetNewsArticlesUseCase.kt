package com.lumine.newsapp.domain.usecase

import com.lumine.newsapp.domain.model.NewsArticle
import com.lumine.newsapp.domain.repository.NewsRepository
import com.lumine.newsapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(page: Int): Flow<Resource<Pair<List<NewsArticle>, Int>>> = flow {
        emit(Resource.Loading())
        val result = repository.getNewsArticles(page, "india")
        emit(result)
    }
}
