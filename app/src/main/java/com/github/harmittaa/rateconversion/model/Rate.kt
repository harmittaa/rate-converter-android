package com.github.harmittaa.rateconversion.model

data class Rate(
    val base: String,
    val date: String,
    var rates: List<SingleRate>
)

data class SingleRate(val code: String, var exchangeRate: Double, var exchangedValue: Double = 0.0)
