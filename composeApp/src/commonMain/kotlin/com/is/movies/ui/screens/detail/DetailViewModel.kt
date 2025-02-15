package com.`is`.movies.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.`is`.movies.data.Movie
import com.`is`.movies.data.MoviesRepository
import kotlinx.coroutines.launch

internal class DetailViewModel(
    private val id: Int,
    private val moviesRepository: MoviesRepository

) : ViewModel() {

    var state by mutableStateOf(UIState())
        private set

    init {
        viewModelScope.launch {
            state = UIState(loading = true)
            state = UIState(loading = false, movie = moviesRepository.fetchMovieById(id))
        }
    }

    data class UIState(
        val loading: Boolean = false,
        val movie: Movie? = null,
    )
}