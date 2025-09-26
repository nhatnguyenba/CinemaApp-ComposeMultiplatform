package org.nhatnb.cinema.data.remote.datasource

import org.nhatnb.cinema.data.remote.api.MovieApi
import org.nhatnb.cinema.data.remote.dto.MovieDetailResponseDto
import org.nhatnb.cinema.data.remote.dto.MovieResponseDto

class MovieRemoteDataSource(
    private val movieApi: MovieApi
) {
    suspend fun getPopularMovies(): MovieResponseDto {
        return movieApi.getPopularMovies()
    }

    suspend fun getMovieDetail(movieId: Long): MovieDetailResponseDto {
        return movieApi.getMovieDetail(movieId)
    }
}