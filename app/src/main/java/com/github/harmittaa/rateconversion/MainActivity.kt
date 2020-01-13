package com.github.harmittaa.rateconversion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.harmittaa.rateconversion.rates.view.RateFragment
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val rateFragment: RateFragment by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                rateFragment, "rate"
            ).commit()
    }
}
