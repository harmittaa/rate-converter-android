package com.github.harmittaa.rateconversion.repository

import com.github.harmittaa.networking.RatesApi
import com.github.harmittaa.rateconversion.model.Rate
import com.github.harmittaa.rateconversion.networking.Resource

class RatesRepository(private val ratesApi: RatesApi) {

    suspend fun getRates(currency: String): Resource<Rate> {
        return try {
            val rates = ratesApi.getRate(baseCurrency = currency)
            Resource.success(rates)
        } catch (e: Exception) {
            Resource.error(e.message ?: "Request failed", null)
        }
    }
}
