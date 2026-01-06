package com.lumine.newsapp.ui.news_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumine.newsapp.domain.model.NewsArticle
import com.lumine.newsapp.domain.usecase.GetArticlesUseCase
import com.lumine.newsapp.util.Constants
import com.lumine.newsapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(NewsListState())
    val state: StateFlow<NewsListState> = _state.asStateFlow()

    init {
        loadArticles()
    }

    fun loadArticles(forceRefresh: Boolean = false) {
        if (forceRefresh) _state.value = NewsListState()

        viewModelScope.launch {
            getArticlesUseCase(_state.value.currentPage).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        val (incoming, total) = result.data!!
                        val merged = if (forceRefresh) incoming
                        else (_state.value.articles + incoming).distinctBy { it.url }

                        _state.value = _state.value.copy(
                            articles = merged,
                            isLoading = false,
                            totalResults = total
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }

    fun loadMore() {
        if (_state.value.isLoading || _state.value.currentPage >= Constants.MAX_PAGES) return

        val nextPage = _state.value.currentPage + 1

        _state.value = _state.value.copy(
            currentPage = nextPage,
            canLoadMore = nextPage < Constants.MAX_PAGES
        )

        loadArticles()
    }


    fun refresh() {
        _state.value = NewsListState()
        loadArticles(forceRefresh = true)
    }
}

data class NewsListState(
    val articles: List<NewsArticle> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 1,
    val canLoadMore: Boolean = true,
    val totalResults: Int = 0,
)
