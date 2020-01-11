package com.github.harmittaa.rateconversion.model

data class Rate(
    val base: String,
    val date: String,
    var rates: List<SingleRate>
)

data class SingleRate(val code: String, val value: Double)
