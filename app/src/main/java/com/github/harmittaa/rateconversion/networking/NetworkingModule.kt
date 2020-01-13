package com.github.harmittaa.rateconversion.networking

import com.github.harmittaa.networking.RetrofitProvider
import org.koin.dsl.module

val networkingModule = module {
    single { RetrofitProvider().provideRetrofit() }
}
