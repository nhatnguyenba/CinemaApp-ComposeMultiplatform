package org.nhatnb.cinema.domain.usecase

import org.nhatnb.cinema.domain.model.Movie
import org.nhatnb.cinema.domain.repository.MovieRepository

class GetPopularMoviesUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): Result<List<Movie>> {
        return movieRepository.getPopularMovies()
    }
}