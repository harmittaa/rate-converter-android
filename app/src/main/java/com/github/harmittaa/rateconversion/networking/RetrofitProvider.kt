package com.github.harmittaa.networking

import com.github.harmittaa.rateconversion.model.Rate
import com.github.harmittaa.rateconversion.networking.RatesParser
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitProvider {

    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(RatesApi.ENDPOINT)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().registerTypeAdapter(Rate::class.java, RatesParser()).create()))
        .build()

    private val service: RatesApi = retrofit.create(RatesApi::class.java)

    suspend fun getRates(currency: String) = service.getRate(currency)
}