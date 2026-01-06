package com.lumine.newsapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lumine.newsapp.ui.news_details.NewsDetailsScreen
import com.lumine.newsapp.ui.news_list.NewsListScreen
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.lumine.newsapp.domain.model.NewsArticle

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.NewsList.route,
            enterTransition = { fadeIn(animationSpec = tween(300)) },
            exitTransition = { fadeOut(animationSpec = tween(300)) }
        ) {
            composable(Screen.NewsList.route) {
                NewsListScreen(
                    onArticleClick = { article ->
                        navController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.set("article", article)
                        navController.navigate(Screen.NewsDetails.route)
                    }
                )
            }

            composable(Screen.NewsDetails.route) {
                val article = navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<NewsArticle>("article")

                NewsDetailsScreen(
                    article = article,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}