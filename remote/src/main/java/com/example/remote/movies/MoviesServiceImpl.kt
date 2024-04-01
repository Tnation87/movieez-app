package com.example.remote.movies

import com.example.common.data.models.MovieDataModel
import com.example.common.data.models.MoviesPageDataModel

class MoviesServiceImpl (private val moviesAPIs: MoviesAPIs): MoviesService {
    override suspend fun getMoviesPage(pageNumber: Int): MoviesPageDataModel {
        return moviesAPIs.getMoviesPage(pageNumber)
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDataModel {
        return moviesAPIs.getMovieDetails(movieId)
    }
}