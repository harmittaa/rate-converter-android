package com.github.harmittaa.rateconversion

import android.app.Application
import com.github.harmittaa.rateconversion.networking.api.apiModule
import com.github.harmittaa.rateconversion.networking.networkingModule
import com.github.harmittaa.rateconversion.rates.rateModule
import com.github.harmittaa.rateconversion.repository.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ConversionApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ConversionApplication)
            modules(listOf(repositoryModule, networkingModule, apiModule, rateModule))
        }
    }
}