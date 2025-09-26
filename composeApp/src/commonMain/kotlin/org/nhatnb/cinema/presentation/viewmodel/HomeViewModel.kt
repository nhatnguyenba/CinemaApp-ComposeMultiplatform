package org.nhatnb.cinema.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.nhatnb.cinema.domain.model.Movie
import org.nhatnb.cinema.domain.usecase.GetMovieDetailUseCase
import org.nhatnb.cinema.domain.usecase.GetPopularMoviesUseCase

class HomeViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    init {
        loadMovies()
    }

    fun loadMovies() {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            errorMessage = null
        )

        coroutineScope.launch {
            try {
                // Fetch popular movies
                val moviesResult = withContext(Dispatchers.IO) {
                    getPopularMoviesUseCase()
                }

                if (moviesResult.isSuccess) {
                    val movies = moviesResult.getOrThrow()
                    val featuredMovie = movies.firstOrNull()

                    _uiState.value = _uiState.value.copy(
                        popularMovies = movies,
                        featuredMovie = featuredMovie,
                        isLoading = false,
                        errorMessage = null
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = moviesResult.exceptionOrNull()?.message
                            ?: "Failed to load movies"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    fun loadMovieDetail(movieId: Long) {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            errorMessage = null
        )

        coroutineScope.launch {
            try {
                val movieDetailResult = withContext(Dispatchers.IO) {
                    getMovieDetailUseCase(movieId)
                }

                if (movieDetailResult.isSuccess) {
                    _uiState.value = _uiState.value.copy(
                        selectedMovie = movieDetailResult.getOrThrow(),
                        isLoading = false,
                        errorMessage = null
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = movieDetailResult.exceptionOrNull()?.message
                            ?: "Failed to load movie details"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    fun clearSelectedMovie() {
        _uiState.value = _uiState.value.copy(
            selectedMovie = null
        )
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(
            errorMessage = null
        )
    }

    fun retry() {
        loadMovies()
    }
}

data class HomeUiState(
    val popularMovies: List<Movie> = emptyList(),
    val featuredMovie: Movie? = null,
    val selectedMovie: Movie? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)