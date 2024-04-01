package com.example.presentation.movies.details

import androidx.compose.runtime.Immutable
import com.example.presentation.UiIntent
import com.example.presentation.UiState
import com.example.presentation.movies.models.MovieUiModel

data object LoadMovieDetailsIntent: UiIntent

@Immutable
sealed class MovieDetailsState: UiState {
    data object Loading: MovieDetailsState()
    data object Error: MovieDetailsState()
    data class Idle(val movie: MovieUiModel): MovieDetailsState()
}