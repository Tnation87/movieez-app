package com.example.presentation.movies.mappers

import com.example.common.data.models.MovieDataModel
import com.example.presentation.movies.models.MovieUiModel

fun MovieDataModel.mapToUiModel() =
    MovieUiModel(
        id = id,
        title = title,
        releaseDate = releaseDate,
        overview = overview,
        posterUrl = "https://image.tmdb.org/t/p/original$posterPath"
    )