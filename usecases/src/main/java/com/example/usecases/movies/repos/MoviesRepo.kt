package com.example.usecases.movies.repos

import com.example.common.data.models.MovieDataModel
import com.example.common.data.models.MoviesPageDataModel

interface MoviesRepo {
    suspend fun getMoviesPage(pageNumber: Int): MoviesPageDataModel
    suspend fun getMovieDetails(movieId: Int): MovieDataModel
}