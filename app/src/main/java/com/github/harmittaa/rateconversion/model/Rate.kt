package com.github.harmittaa.rateconversion.model

data class Rate(
    val base: String,
    val date: String,
    val rates: Rates
)