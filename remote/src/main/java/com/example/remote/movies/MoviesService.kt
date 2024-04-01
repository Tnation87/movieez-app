package com.example.remote.movies

import com.example.common.data.models.MovieDataModel
import com.example.common.data.models.MoviesPageDataModel

interface MoviesService {
    suspend fun getMoviesPage(pageNumber: Int): MoviesPageDataModel
    suspend fun getMovieDetails(movieId: Int): MovieDataModel
}