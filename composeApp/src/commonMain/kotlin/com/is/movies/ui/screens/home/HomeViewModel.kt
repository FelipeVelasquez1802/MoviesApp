package com.`is`.movies.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.`is`.movies.Movie
import com.`is`.movies.movies
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class HomeViewModel : ViewModel() {

    var state by mutableStateOf(UIState())
        private set

    init {
        viewModelScope.launch {
            state = UIState(loading = true)
            delay(1000)
            state = UIState(loading = false, movies = movies)
        }
    }

    data class UIState(
        val loading: Boolean = false,
        val movies: List<Movie> = emptyList(),
    )
}