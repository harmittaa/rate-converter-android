package com.github.harmittaa.rateconversion.repository

import com.github.harmittaa.networking.RetrofitProvider
import com.github.harmittaa.rateconversion.model.Rate
import com.github.harmittaa.rateconversion.networking.Resource

class RatesRepository {
    private var networkProvider = RetrofitProvider()

    suspend fun getRates(forCurrency: String): Resource<Rate> {
        return try {
            val rates = networkProvider.getRates(forCurrency)
            Resource.success(rates)
        } catch (e: Exception) {
            Resource.error(e.message ?: "Request failed", null)
        }
    }
}
