package com.example.presentation.movies.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.presentation.BaseViewModel
import com.example.presentation.IoDispatcher
import com.example.presentation.NavigationArgs.MOVIE_ID_ARG
import com.example.presentation.movies.mappers.mapToUiModel
import com.example.usecases.movies.details.LoadMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @IoDispatcher val backgroundDispatcher: CoroutineDispatcher,
    private val loadMovieDetailsUseCase: LoadMovieDetailsUseCase
) : BaseViewModel<LoadMovieDetailsIntent, MovieDetailsState>() {

    private val movieId: String =
        checkNotNull(savedStateHandle[MOVIE_ID_ARG]) { "You have to send the movie id as an argument to the MovieDetailsViewModel" }

    override fun createInitialState(): MovieDetailsState {
        return MovieDetailsState.Loading
    }

    override fun handleIntent(intent: LoadMovieDetailsIntent) {
        setState { MovieDetailsState.Loading }
        viewModelScope.launch(backgroundDispatcher) {
            val movieDetails = loadMovieDetailsUseCase(movieId.toInt())
            setState {
                if (movieDetails != null)
                    MovieDetailsState.Idle(movieDetails.mapToUiModel())
                else MovieDetailsState.Error
            }
        }
    }
}