package com.example.presentation.movies.list

import androidx.lifecycle.viewModelScope
import com.example.presentation.BaseViewModel
import com.example.presentation.IoDispatcher
import com.example.presentation.movies.mappers.mapToUiModel
import com.example.usecases.movies.list.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    @IoDispatcher val backgroundDispatcher: CoroutineDispatcher,
    private val getMoviesUseCase: GetMoviesUseCase
) :
    BaseViewModel<MoviesListIntent, MoviesListState>() {
    override fun createInitialState(): MoviesListState {
        return MoviesListState(
            movies = persistentListOf(),
            isLoadingNewItems = true,
            isError = false,
            currentPage = 0,
            // assuming any initial number that would allow the first page to be fetched
            totalNumberOfPages = 1
        )
    }

    override fun handleIntent(intent: MoviesListIntent) {
        when (intent) {
            MoviesListIntent.ConsumeErrorIntent -> setState { copy(isError = false) }
            MoviesListIntent.GetNextPageIntent -> getNextPage()
        }
    }

    private fun getNextPage() {
        setState { copy(isLoadingNewItems = true) }

        viewModelScope.launch(backgroundDispatcher) {
            val moviesPage = getMoviesUseCase(currentState.currentPage + 1)
            setState {
                if (moviesPage != null)
                    MoviesListState(
                        movies = movies.addAll(moviesPage.movies.map { it.mapToUiModel() }),
                        isLoadingNewItems = false,
                        isError = false,
                        currentPage = moviesPage.currentPage,
                        totalNumberOfPages = moviesPage.totalNumberOfPages
                    )
                else copy(isLoadingNewItems = false, isError = true)
            }
        }
    }
}