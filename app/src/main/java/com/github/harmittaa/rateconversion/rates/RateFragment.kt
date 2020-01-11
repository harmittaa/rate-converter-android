package com.github.harmittaa.rateconversion.rates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.harmittaa.rateconversion.R
import com.github.harmittaa.rateconversion.model.Rate
import com.github.harmittaa.rateconversion.model.SingleRate
import kotlinx.android.synthetic.main.fragment_rate.*
import kotlin.random.Random

class RateFragment : Fragment() {

    private lateinit var viewModel: RateViewModel
    private val rateAdapter = RateAdapter(emptyList())


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel = ViewModelProvider(this).get(RateViewModel::class.java)
        viewModel.rates.observe(viewLifecycleOwner, ratesObserver)
        return inflater.inflate(R.layout.fragment_rate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rateAdapter.listener = viewModel
        ratesList.apply {
            layoutManager = LinearLayoutManager(this@RateFragment.context)
            adapter = rateAdapter
        }
        viewModel.getRates()
    }

    private val ratesObserver = Observer<List<SingleRate>> {
        rateAdapter.list = it
        rateAdapter.notifyDataSetChanged()
    }
}