package org.nhatnb.cinema.data.repository

import org.nhatnb.cinema.data.remote.datasource.MovieRemoteDataSource
import org.nhatnb.cinema.data.remote.dto.MovieDto
import org.nhatnb.cinema.domain.model.Movie
import org.nhatnb.cinema.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource
) : MovieRepository {

    override suspend fun getPopularMovies(): Result<List<Movie>> {
        return try {
            val response = remoteDataSource.getPopularMovies()
            Result.success(response.results.map { it.toMovie() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMovieDetail(movieId: Long): Result<Movie> {
        return try {
            val response = remoteDataSource.getMovieDetail(movieId)
            // Giả sử API trả về danh sách, lấy phần tử đầu tiên
            val movieDto = response.results.firstOrNull()
                ?: throw IllegalArgumentException("Movie not found")
            Result.success(movieDto.toMovie())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchMovies(query: String): Result<List<Movie>> {
        // Triển khai sau
        return Result.success(emptyList())
    }

    private fun MovieDto.toMovie(): Movie {
        return Movie(
            id = id,
            title = title,
            overview = overview,
            posterPath = posterPath,
            voteAverage = voteAverage,
            releaseDate = releaseDate,
            genreIds = genreIds
        )
    }
}