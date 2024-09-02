package com.max77.freedomfinanceeval.ui.viewmodel

sealed interface UiState<T> {
    data class Data<T>(val data: T) : UiState<T>
    class Loading<T> : UiState<T>
    data class Error<T>(val message: String) : UiState<T>
}
