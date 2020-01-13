package com.github.harmittaa.rateconversion.rates.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.harmittaa.rateconversion.R
import com.github.harmittaa.rateconversion.model.SingleRate
import com.github.harmittaa.rateconversion.rates.RateAdapter
import com.github.harmittaa.rateconversion.rates.RateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.android.synthetic.main.fragment_rate.*

class RateFragment : Fragment() {
    private val viewModel: RateViewModel by viewModel()
    private val rateAdapter = RateAdapter(emptyList())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel.rates.observe(viewLifecycleOwner, ratesObserver)
        return inflater.inflate(R.layout.fragment_rate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rateAdapter.listener = viewModel
        rateAdapter.setHasStableIds(true)
        ratesList.apply {
            layoutManager = LinearLayoutManager(this@RateFragment.context)
            adapter = rateAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.startRateUpdates()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopRateUpdates()
    }

    private val ratesObserver = Observer<List<SingleRate>> {
        rateAdapter.list = it
        rateAdapter.notifyDataSetChanged()
    }
}