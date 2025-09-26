package org.nhatnb.cinema.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.nhatnb.cinema.data.remote.dto.MovieDetailResponseDto
import org.nhatnb.cinema.data.remote.dto.MovieResponseDto

class MovieApi(private val client: HttpClient) {

    suspend fun getPopularMovies(): MovieResponseDto {
        return client.get("${ApiConstants.BASE_URL}${ApiConstants.Endpoints.POPULAR_MOVIES}") {
            parameter("api_key", ApiConstants.API_KEY)
            parameter("language", "en-US")
            parameter("page", 1)
        }.body()
    }

    suspend fun getMovieDetail(movieId: Long): MovieDetailResponseDto {
        return client.get(
            "${ApiConstants.BASE_URL}${
                ApiConstants.Endpoints.MOVIE_DETAIL.replace("{movie_id}", movieId.toString())
            }"
        ) {
            parameter("api_key", ApiConstants.API_KEY)
        }.body()
    }
}