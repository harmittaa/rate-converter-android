package com.github.harmittaa.rateconversion.repository

import com.github.harmittaa.networking.RatesApi
import com.github.harmittaa.rateconversion.model.Rate
import com.github.harmittaa.rateconversion.model.SingleRate
import com.github.harmittaa.rateconversion.networking.Status
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import retrofit2.HttpException

class RatesRepositoryTest {
    private lateinit var repository: RatesRepository
    private lateinit var api: RatesApi
    private val validCurrency = "EUR"
    private val invalidCurrency = "ABC"
    private val rate = Rate(validCurrency, "", listOf(SingleRate("BGN", 1.2, currencyName = "", id = 1)))


    @Before
    fun setUp() {
        api = mock()
        runBlocking {
            whenever(api.getRate(validCurrency)).thenReturn(rate)
            whenever(api.getRate(invalidCurrency)).thenThrow(mock<HttpException>())
        }
        repository = RatesRepository(api)
    }

    @Test
    fun `test when api responds rate then return success with rate`() = runBlocking {
        val response = repository.getRates(validCurrency)
        assertEquals(Status.SUCCESS, response.status)
        assertSame(rate, response.data)
        assertSame(rate, response.data)
    }

    @Test
    fun `test when api responds with error then return error`() = runBlocking {
        val response = repository.getRates(invalidCurrency)
        assertEquals(Status.ERROR, response.status)
        assertEquals("Request failed", response.message)
    }


}