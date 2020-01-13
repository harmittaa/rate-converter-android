package com.github.harmittaa.rateconversion.networking

import com.google.gson.JsonObject
import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.lang.IllegalArgumentException

@RunWith(JUnit4::class)
class RatesParserTest {
    private lateinit var parser: RatesParser

    @Before
    fun setUp() {
        parser = RatesParser()
    }

    @Test
    fun `test response is converted to a list with base rate as the first item`() {
        // given
        val rootObject = addCurrencyProperty(createBaseRate("EUR"), "BGN")
        // when
        val response = parser.deserialize(rootObject, null, null)
        // then
        assertEquals("EUR", response.base)
        assertEquals(2, response.rates.size)
    }

    @Test
    fun `test deserialized response has correct conversion rate`() {
        val expectedConversionRate = 1.234567
        // given
        val rootObject = addCurrencyProperty(createBaseRate("EUR"), "BGN", expectedConversionRate)
        // when
        val response = parser.deserialize(rootObject, null, null)
        // then
        assertEquals(expectedConversionRate, response.rates.first { it.code == "BGN" }.exchangeRate, 0.0)
    }

    @Test
    fun `test base rate gets stable id`() {
        var rootObject = addCurrencyProperty(createBaseRate("EUR"), "BGN")

        val response = parser.deserialize(rootObject, null, null)

        assertEquals("EUR", response.base)
        assertEquals(0, response.rates.first { it.code == "EUR" }.id)

        rootObject = addCurrencyProperty(createBaseRate("BGN"), "EUR")

        val secondResponse = parser.deserialize(rootObject, null, null)
        assertEquals("BGN", secondResponse.base)
        assertEquals(0, secondResponse.rates.first { it.code == "EUR" }.id)
    }

    @Test
    fun `test id remains stable even when new rate is introduced`() {
        var rootObject = addCurrencyProperty(createBaseRate("EUR"), "BGN")
        val expectedBgnId = 1

        val response = parser.deserialize(rootObject, null, null)
        assertEquals(expectedBgnId, response.rates.first { it.code == "BGN" }.id)

        rootObject = addCurrencyProperty(createBaseRate("EUR"), "MXN")

        parser.deserialize(rootObject, null, null)

        rootObject = addCurrencyProperty(createBaseRate("MXN"), "BGN")

        val thirdResponse = parser.deserialize(rootObject, null, null)
        assertEquals(expectedBgnId, thirdResponse.rates.first { it.code == "BGN" }.id)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test when JSON object is null exception is thrown`() {
        parser.deserialize(null, null, null)
    }

    private fun createBaseRate(baseProperty: String): JsonObject {
        val base = JsonObject()
        base.addProperty("base", baseProperty)
        base.addProperty("date", "2018-09-06")
        return base
    }

    private fun addCurrencyProperty(toObject: JsonObject, code: String, value: Double = 1.123): JsonObject {
        val rateObject = JsonObject()
        rateObject.addProperty(code, value)
        toObject.add("rates", rateObject)
        return toObject
    }

}