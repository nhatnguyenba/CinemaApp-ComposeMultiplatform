package org.nhatnb.cinema.domain.model

import org.nhatnb.cinema.data.remote.api.ApiConstants

data class Movie(
    val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val voteAverage: Double,
    val releaseDate: String,
    val genreIds: List<Long> = emptyList(),
    val backdropPath: String? = null,
    val popularity: Double = 0.0,
    val voteCount: Long = 0
) {
    val rating: String
        get() = "%.1f".format(voteAverage)

    val releaseYear: String
        get() = releaseDate.take(4)

    val fullPosterUrl: String
        get() = "${ApiConstants.IMAGE_BASE_URL}$posterPath"

    val fullBackdropUrl: String
        get() = "${ApiConstants.IMAGE_BASE_URL}${backdropPath ?: posterPath}"
}