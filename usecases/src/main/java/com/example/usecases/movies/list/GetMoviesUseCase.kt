package com.example.usecases.movies.list

import com.example.common.data.models.MoviesPageDataModel
import com.example.usecases.movies.repos.MoviesRepo
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val moviesRepo: MoviesRepo) {
    suspend operator fun invoke(pageNumber: Int): MoviesPageDataModel? = try {
        moviesRepo.getMoviesPage(pageNumber)
    } catch (_: Exception) {
        null
    }
}