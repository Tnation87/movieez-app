package com.example.usecases.movies.details

import com.example.common.data.models.MovieDataModel
import com.example.usecases.movies.repos.MoviesRepo
import javax.inject.Inject

class LoadMovieDetailsUseCase @Inject constructor(private val moviesRepo: MoviesRepo) {
    suspend operator fun invoke(movieId: Int): MovieDataModel? = try {
        moviesRepo.getMovieDetails(movieId)
    } catch (_: Exception) {
        null
    }
}