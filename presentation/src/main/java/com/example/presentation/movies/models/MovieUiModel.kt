package com.example.presentation.movies.models

import androidx.compose.runtime.Immutable
import com.example.presentation.UiModel

@Immutable
data class MovieUiModel(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val overview: String,
    val posterUrl: String
): UiModel
