package com.example.repo.movies

import com.example.common.data.models.MovieDataModel
import com.example.common.data.models.MoviesPageDataModel
import com.example.remote.movies.MoviesService
import com.example.usecases.movies.repos.MoviesRepo

class MoviesRepoImpl (private val moviesService: MoviesService): MoviesRepo {
    override suspend fun getMoviesPage(pageNumber: Int): MoviesPageDataModel {
        return moviesService.getMoviesPage(pageNumber)
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDataModel {
        return moviesService.getMovieDetails(movieId)
    }
}