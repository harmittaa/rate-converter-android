package com.github.harmittaa.rateconversion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.harmittaa.networking.RatesApi
import com.github.harmittaa.rateconversion.rates.RateFragment
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, RateFragment(), "rate").commit()
    }
}
