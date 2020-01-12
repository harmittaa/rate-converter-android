package com.github.harmittaa.rateconversion.rates

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.harmittaa.rateconversion.model.SingleRate
import com.github.harmittaa.rateconversion.repository.RatesRepository
import kotlinx.coroutines.launch
import kotlin.concurrent.fixedRateTimer

class RateViewModel : ViewModel(), RateRowListener {
    private val repository = RatesRepository()
    val rates = MutableLiveData<List<SingleRate>>()
    private var currentRate = SingleRate(code = "EUR", exchangeRate = 1.0, currencyName = "Euro")
    private var currentRates = listOf<SingleRate>()

    fun getRates() {
        fixedRateTimer("ratesFetcher", false, 0, 1_000) {
            fetchRates()
        }
    }

    fun fetchRates() {
        viewModelScope.launch {
            val newRates = repository.getRates(forCurrency = currentRate.code).rates
            newRates.forEach { rate -> rate.exchangedValue = rate.exchangeRate * currentRate.exchangedValue }
            currentRates = newRates
            rates.value = listOf(currentRate) + newRates
        }
    }

    override fun onRowFocused(row: Int) {
        if (row <= 0) return
        currentRate = currentRates[row-1]
        currentRate.exchangeRate = 1.0
        fetchRates()
    }

    override fun onInputChanged(newInput: Double) {
        currentRate.exchangedValue = newInput
        viewModelScope.launch {
            currentRates.forEach { rate -> rate.exchangedValue = rate.exchangeRate * newInput }
            rates.value = listOf(currentRate) + currentRates
        }
    }
}
