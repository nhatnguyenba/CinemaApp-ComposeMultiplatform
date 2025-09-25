package org.nhatnb.cinema.di

import org.koin.dsl.module
import org.nhatnb.cinema.domain.usecase.GetMovieDetailUseCase
import org.nhatnb.cinema.domain.usecase.GetPopularMoviesUseCase

val domainModule = module {
    factory { GetPopularMoviesUseCase(get()) }
    factory { GetMovieDetailUseCase(get()) }
}