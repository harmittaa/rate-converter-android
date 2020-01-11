package com.github.harmittaa.rateconversion.rates

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.harmittaa.networking.RetrofitProvider
import com.github.harmittaa.rateconversion.model.Rate
import com.github.harmittaa.rateconversion.repository.RatesRepository
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class RateViewModel : ViewModel() {
    private val repository = RatesRepository()
    val rates = MutableLiveData<Rate>()

    fun getRates(currency: String) {
        viewModelScope.launch {
            rates.value = repository.getRates(forCurrency = currency)
        }
    }
}