package org.nhatnb.cinema.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import org.nhatnb.cinema.data.remote.api.MovieApi
import org.nhatnb.cinema.data.remote.datasource.MovieRemoteDataSource
import org.nhatnb.cinema.data.repository.MovieRepositoryImpl
import org.nhatnb.cinema.domain.repository.MovieRepository

val dataModule = module {
    single<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("HTTP Client: $message")
                    }
                }
                level = LogLevel.ALL
            }
        }
    }

    single<MovieApi> { MovieApi(get()) }
    single<MovieRemoteDataSource> { MovieRemoteDataSource(get()) }
    single<MovieRepository> { MovieRepositoryImpl(get()) }
}