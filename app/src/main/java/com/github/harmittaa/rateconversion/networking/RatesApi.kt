package com.github.harmittaa.networking

import com.github.harmittaa.rateconversion.model.Rate
import com.github.harmittaa.rateconversion.model.Rates
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesApi {

    @GET("latest")
    suspend fun getRate(@Query("base") baseCurrency: String): Rate

    companion object {
        const val ENDPOINT = "https://revolut.duckdns.org/"
    }
}