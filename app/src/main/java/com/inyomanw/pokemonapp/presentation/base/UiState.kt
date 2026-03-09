package com.inyomanw.pokemonapp.presentation.base

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    object Init : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}