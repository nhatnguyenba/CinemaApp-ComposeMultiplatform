package org.nhatnb.cinema.di

import org.koin.dsl.module
import org.nhatnb.cinema.presentation.viewmodel.HomeViewModel

val presentationModule = module {
    factory { HomeViewModel(get(), get()) }
}