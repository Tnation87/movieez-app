package com.example.movieez.ui.nav

import com.example.presentation.NavigationArgs.MOVIE_ID_ARG

enum class Routes(val route: String) {
    MOVIES_LIST_ROUTE("movies"),
    MOVIE_DETAILS_ROUTE("movie_details/{$MOVIE_ID_ARG}")
}
