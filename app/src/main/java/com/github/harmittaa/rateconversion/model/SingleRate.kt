package com.github.harmittaa.rateconversion.model

data class SingleRate(val code: String,
                      var exchangeRate: Double,
                      var exchangedValue: Double = 0.0,
                      val currencyName: String,
                      val id: Int
)