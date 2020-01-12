package com.github.harmittaa.rateconversion.networking

import com.github.harmittaa.rateconversion.model.Rate
import com.github.harmittaa.rateconversion.model.SingleRate
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.*

class RatesParser : JsonDeserializer<Rate> {
    private val idMap = mutableMapOf<String, Int>()

    // JSON response doesn't contain a list of rates, so the response is converted here
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Rate {
        val jsonResponse = json!!.asJsonObject!!
        val ratesObject = jsonResponse.getAsJsonObject("rates")
        val rateEntries = ratesObject.entrySet()

        val baseRateKey = jsonResponse.get("base").asString
        val baseRateId = resolveId(code = baseRateKey, index = 0)
        val baseRate = SingleRate(code = jsonResponse.get("base").asString, id = baseRateId, exchangeRate = 1.0, exchangedValue = 0.0, currencyName = Currency.getInstance(baseRateKey).displayName)

        val simpleRates = rateEntries.mapIndexed { index, rate ->
            // index is increment as the base rate will have idx 0 on the first go
            // after the first request each currency will have been mapped to a stable ID
            val idx = index + 1
            val id = resolveId(code = rate.key, index = idx)
            SingleRate(id = id, code = rate.key, exchangeRate = rate.value.asDouble,
            currencyName = Currency.getInstance(rate.key).displayName)
        }
        return Rate(base = jsonResponse.get("base").asString, date = jsonResponse.get("date").asString, rates = listOf(baseRate) + simpleRates)
    }

    private fun resolveId(code: String, index: Int): Int {
        if (idMap.containsKey(code)) {
            return idMap[code]!!
        }

        idMap[code] = index
        return index
    }
}