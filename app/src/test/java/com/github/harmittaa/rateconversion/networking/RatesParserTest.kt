package com.github.harmittaa.rateconversion.networking

import com.google.gson.JsonObject
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RatesParserTest {
    private lateinit var parser: RatesParser

    @Before
    fun setUp() {
        parser = RatesParser()
    }

    @Test
    fun `test response is converted to a list with base rate as the first item`() {
        val jsonObject = JsonObject()
        val bgnProp = JsonObject()
        jsonObject.addProperty("base", "EUR")
        jsonObject.addProperty("date", "2018-09-06")
        bgnProp.addProperty("BGN", 1.2076)
        jsonObject.add("rates", bgnProp)

        val response = parser.deserialize(jsonObject, null, null)
        assertEquals("EUR", response.base)
        assertEquals(2, response.rates.size)
    }

    @Test
    fun `test base rate gets stable id`() {
        val jsonObject = JsonObject()
        val bgnProp = JsonObject()

        jsonObject.addProperty("base", "EUR")
        jsonObject.addProperty("date", "2018-09-06")
        bgnProp.addProperty("BGN", 1.2076)
        jsonObject.add("rates", bgnProp)

        val response = parser.deserialize(jsonObject, null, null)
        assertEquals("EUR", response.base)
        assertEquals(0, response.rates.first { it.code == "EUR" }.id)

        jsonObject.addProperty("base", "BGN")
        jsonObject.addProperty("date", "2018-09-06")
        bgnProp.addProperty("EUR", 123.45)
        jsonObject.add("rates", bgnProp)

        val secondResponse = parser.deserialize(jsonObject, null, null)
        assertEquals("BGN", secondResponse.base)
        assertEquals(0, secondResponse.rates.first { it.code == "EUR" }.id)

    }
}