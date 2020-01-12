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
    private var currentRate: SingleRate? = null
    private var currentRates = listOf<SingleRate>()

    fun getRates() {
        fixedRateTimer("ratesFetcher", false, 0, 1_000) {
            fetchRates()
        }
    }

    private fun fetchRates() {
        viewModelScope.launch {
            val newRates = repository.getRates(forCurrency = currentRate?.code ?: "EUR").rates
            newRates.forEach { rate -> rate.exchangedValue = rate.exchangeRate * (currentRate?.exchangedValue
                ?: 0.0) }
            currentRates = newRates
            currentRate = newRates[0]
            rates.value = currentRates
        }
    }

    override fun onRowFocused(itemId: Int) {
        currentRate = currentRates.first { it.id == itemId }
        currentRate!!.exchangeRate = 1.0
        fetchRates()
    }

    override fun onInputChanged(newInput: Double) {
        currentRate!!.exchangedValue = newInput
        viewModelScope.launch {
            currentRates.forEach { rate -> rate.exchangedValue = rate.exchangeRate * newInput }
            rates.value = currentRates
        }
    }
}
