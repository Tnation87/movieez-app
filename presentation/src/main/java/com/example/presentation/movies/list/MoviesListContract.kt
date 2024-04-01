package com.example.presentation.movies.list

import androidx.compose.runtime.Immutable
import com.example.presentation.UiIntent
import com.example.presentation.UiState
import com.example.presentation.movies.models.MovieUiModel
import kotlinx.collections.immutable.PersistentList

sealed class MoviesListIntent : UiIntent {
    data object GetNextPageIntent: MoviesListIntent()
    data object ConsumeErrorIntent: MoviesListIntent()
}

@Immutable
data class MoviesListState(
    val movies: PersistentList<MovieUiModel>,
    val isLoadingNewItems: Boolean,
    val isError: Boolean,
    val currentPage: Int,
    val totalNumberOfPages: Int
) : UiState {
    val hasReachedEnd = currentPage == totalNumberOfPages

    val indexOfLastScrollItemWithPadding = movies.lastIndex - 5
}