package com.github.harmittaa.rateconversion.rates

import com.github.harmittaa.rateconversion.rates.view.RateFragment
import org.koin.dsl.module

val rateModule = module {
    factory { RateFragment() }
    factory { RateViewModel(get()) }
}