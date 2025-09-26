package org.nhatnb.cinema.data.remote.api

object ApiConstants {
    const val API_KEY = "4b70247a521474f157f9fb7320bb7cc9"
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    const val IMAGE_ORIGINAL_URL = "https://image.tmdb.org/t/p/original"

    object Endpoints {
        const val POPULAR_MOVIES = "movie/popular"
        const val MOVIE_DETAIL = "movie/{movie_id}"
        const val SEARCH_MOVIES = "search/movie"
    }
}