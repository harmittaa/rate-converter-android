package com.github.harmittaa.rateconversion.repository

import com.github.harmittaa.networking.RetrofitProvider
import com.github.harmittaa.rateconversion.model.Rate

class RatesRepository {
    private var networkProvider: RetrofitProvider = RetrofitProvider()

    suspend fun getRates(forCurrency: String): Rate = networkProvider.getRates(forCurrency)
}
