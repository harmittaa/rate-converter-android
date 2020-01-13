package com.github.harmittaa.rateconversion.rates

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.harmittaa.rateconversion.model.SingleRate
import com.github.harmittaa.rateconversion.networking.Status
import com.github.harmittaa.rateconversion.repository.RatesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.fixedRateTimer

class RateViewModel(private val repository: RatesRepository) : ViewModel(), RateRowListener {
    @VisibleForTesting val defaultCurrency = "EUR"
    @VisibleForTesting var rateFetchingPeriod = 1_000L
    private lateinit var timer: Timer
    private var currentRate: SingleRate? = null
    private var currentRates = listOf<SingleRate>()

    private val _rates = MutableLiveData<List<SingleRate>>()
    val rates: LiveData<List<SingleRate>>
        get() = _rates

    fun startRateUpdates() {
        timer = fixedRateTimer("ratesFetcher", false, 0, rateFetchingPeriod) {
            fetchRates()
        }
    }

    fun stopRateUpdates() {
        timer.cancel()
    }

    private fun fetchRates() {
        viewModelScope.launch(context = Dispatchers.IO) {
            // default to EUR for the first request
            val newRates = repository.getRates(currency = currentRate?.code ?: defaultCurrency)
            when (newRates.status) {
                Status.ERROR -> Log.e("VM", "Request failed $newRates.message")
                else -> handleSuccess(newRates.data!!.rates)
            }
        }
    }

    private fun handleSuccess(rates: List<SingleRate>) {
        rates.forEach { rate ->
            rate.exchangedValue = rate.exchangeRate * (currentRate?.exchangedValue
                ?: 0.0)
        }
        currentRates = rates
        currentRate = rates[0]
        _rates.postValue(currentRates)
    }

    override fun onRowFocused(itemId: Int) {
        currentRate = currentRates.first { it.id == itemId }
        currentRate?.exchangeRate = 1.0
        fetchRates()
    }

    override fun onInputChanged(newInput: Double) {
        currentRate?.exchangedValue = newInput
        viewModelScope.launch(context = Dispatchers.Default) {
            currentRates.forEach { rate -> rate.exchangedValue = rate.exchangeRate * newInput }
            _rates.postValue(currentRates)
        }
    }
}
