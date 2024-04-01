package com.example.remote.movies

import com.example.common.data.models.MovieDataModel
import com.example.common.data.models.MoviesPageDataModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesAPIs {
    @GET("discover/movie")
    suspend fun getMoviesPage(@Query("page") pageNumber: Int): MoviesPageDataModel

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): MovieDataModel
}