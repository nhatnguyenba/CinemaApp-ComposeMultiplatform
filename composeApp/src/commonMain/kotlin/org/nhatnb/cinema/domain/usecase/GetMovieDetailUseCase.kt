package org.nhatnb.cinema.domain.usecase

import org.nhatnb.cinema.domain.model.Movie
import org.nhatnb.cinema.domain.repository.MovieRepository

class GetMovieDetailUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Long): Result<Movie> {
        return movieRepository.getMovieDetail(movieId)
    }
}