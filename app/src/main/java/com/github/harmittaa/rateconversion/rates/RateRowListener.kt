package com.github.harmittaa.rateconversion.rates

interface RateRowListener {
    fun onInputChanged(newInput: Double)
    fun onRowFocused(itemId: Int)
}