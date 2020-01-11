package com.github.harmittaa.rateconversion.rates

import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.harmittaa.rateconversion.R
import com.github.harmittaa.rateconversion.model.Rate
import kotlinx.android.synthetic.main.fragment_rate.*

class RateFragment : Fragment() {

    private lateinit var viewModel: RateViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel = ViewModelProvider(this).get(RateViewModel::class.java)
        viewModel.rates.observe(viewLifecycleOwner, ratesObserver)
        return inflater.inflate(R.layout.fragment_rate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getRatesButton.setOnClickListener {
            viewModel.getRates("EUR")
        }

    }

    private val ratesObserver = Observer<Rate> {
        Log.d("THIS", it.date)
    }
}