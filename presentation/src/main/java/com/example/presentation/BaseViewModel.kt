package com.example.presentation

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface UiState

interface UiIntent

abstract class BaseViewModel<Intent : UiIntent, State : UiState> : ViewModel() {

    // Create Initial State of View
    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State

    // Get Current State
    val currentState: State
        get() = uiState.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _intent: MutableSharedFlow<Intent> = MutableSharedFlow()
    private val intent = _intent.asSharedFlow()

    /**
     * Set new Intent
     */
    fun setIntent(intent: Intent) {
        Log.d("MVI", "Intent detected: $intent")
        viewModelScope.launch { _intent.emit(intent) }
    }

    /**
     * Set new Ui State
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun setState(reduce: State.() -> State) {
        _uiState.update {
            val res = it.reduce()
            Log.d("MVI", "ViewState detected: $res")
            res
        }
    }

    init {
        subscribeIntents()
    }

    /**
     * Start listening to Intents
     */
    private fun subscribeIntents() {
        viewModelScope.launch {
            intent.collect {
                handleIntent(it)
            }
        }
    }

    /**
     * Handle each intent
     */
    protected abstract fun handleIntent(intent: Intent)
}