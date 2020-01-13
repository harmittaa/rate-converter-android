package com.github.harmittaa.rateconversion.networking.api

import com.github.harmittaa.networking.RatesApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module { factory { provideRatesApi(get()) } }

private fun provideRatesApi(retrofit: Retrofit): RatesApi = retrofit.create(RatesApi::class.java)
