package com.github.harmittaa.rateconversion.repository

import org.koin.dsl.module

val repositoryModule = module {
    factory { RatesRepository(get()) }
}