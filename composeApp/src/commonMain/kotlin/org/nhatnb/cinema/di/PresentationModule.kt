package org.nhatnb.cinema.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.nhatnb.cinema.presentation.viewmodel.HomeViewModel

val presentationModule = module {
    viewModelOf(::HomeViewModel)
}