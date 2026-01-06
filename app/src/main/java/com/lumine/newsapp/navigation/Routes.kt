package com.lumine.newsapp.navigation

sealed class Screen(val route: String) {
    data object NewsList : Screen("news_list")
    data object NewsDetails : Screen("news_details")
}
