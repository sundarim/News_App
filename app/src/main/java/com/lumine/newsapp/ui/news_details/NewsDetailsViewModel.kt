package com.lumine.newsapp.ui.news_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.lumine.newsapp.domain.model.NewsArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val article: NewsArticle? = savedStateHandle.get<NewsArticle>("article")
}