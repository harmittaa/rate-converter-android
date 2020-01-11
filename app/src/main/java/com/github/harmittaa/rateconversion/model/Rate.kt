package com.github.harmittaa.rateconversion.model

data class Rate(
    val base: String,
    val date: String,
    var rates: Rates
) {

    init {
    }
}