package org.nhatnb.cinema

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.nhatnb.cinema.di.initKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MyApp)
        }
    }
}