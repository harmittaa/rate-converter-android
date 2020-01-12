package com.github.harmittaa.rateconversion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.harmittaa.rateconversion.rates.view.RateFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        supportFragmentManager.beginTransaction()
            .replace(R.id.container,
                RateFragment(), "rate").commit()
    }
}
