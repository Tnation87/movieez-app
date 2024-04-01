package com.example.movieez.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieez.ui.movies.details.MovieDetailsScreen
import com.example.movieez.ui.movies.list.MoviesListScreen
import com.example.presentation.NavigationArgs.MOVIE_ID_ARG

@Composable
fun HomeGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.MOVIES_LIST_ROUTE.route
    ) {
        composable(Routes.MOVIES_LIST_ROUTE.route) {
            MoviesListScreen { movieId ->
                navController.navigate("${Routes.MOVIE_DETAILS_ROUTE.route}/$movieId")
            }
        }
        composable(
            route = "${Routes.MOVIE_DETAILS_ROUTE.route}/{$MOVIE_ID_ARG}"
        ) {
            MovieDetailsScreen()
        }
    }
}
