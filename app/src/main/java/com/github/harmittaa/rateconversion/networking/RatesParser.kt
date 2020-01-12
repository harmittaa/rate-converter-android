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


    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Rate {
        val jsonResponse = json!!.asJsonObject!!
        val ratesObject = jsonResponse.getAsJsonObject("rates")
        val rateEntries = ratesObject.entrySet()
        val simpleRates = rateEntries.mapIndexed { index, rate ->

            val rateId = if (idMap.containsKey(rate.key)) idMap[rate.key] else index
            SingleRate(id = rateId!!, code = rate.key, exchangeRate = rate.value.asDouble,
            currencyName = Currency.getInstance(rate.key).displayName)

        }
        return Rate(base = jsonResponse.get("base").asString, date = jsonResponse.get("date").asString, rates = simpleRates)
    }
}