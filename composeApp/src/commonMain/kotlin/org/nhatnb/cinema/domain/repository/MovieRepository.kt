package org.nhatnb.cinema.domain.repository

import org.nhatnb.cinema.domain.model.Movie

interface MovieRepository {
    suspend fun getPopularMovies(): Result<List<Movie>>
    suspend fun getMovieDetail(movieId: Long): Result<Movie>
    suspend fun searchMovies(query: String): Result<List<Movie>>
}