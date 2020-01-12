package com.github.harmittaa.networking

import com.github.harmittaa.rateconversion.model.Rate
import com.github.harmittaa.rateconversion.networking.RatesParser
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitProvider {

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(RatesApi.ENDPOINT)
        .client(provideOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().registerTypeAdapter(Rate::class.java, RatesParser()).create()))
        .build()

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(provideLoggingInterceptor()).build()
    }

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC
        return logger
    }

    private val service: RatesApi = retrofit.create(RatesApi::class.java)

    suspend fun getRates(currency: String) = service.getRate(currency)
}