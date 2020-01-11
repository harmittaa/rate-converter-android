package com.github.harmittaa.rateconversion.rates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.harmittaa.rateconversion.R
import com.github.harmittaa.rateconversion.model.SingleRate

class RateAdapter(var list: List<SingleRate>) : RecyclerView.Adapter<RateAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val row = LayoutInflater.from(parent.context)
            .inflate(R.layout.rate_row, parent, false) as View
        return ViewHolder(row)
    }

    override fun getItemCount() = list.count()


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setItem(list[position])
    }


    class ViewHolder(private val item: View) : RecyclerView.ViewHolder(item) {
        private val code: TextView = item.findViewById(R.id.currencyCode)
        private val name: TextView = item.findViewById(R.id.currencyName)
        fun setItem(item: SingleRate) {
            code.text = item.code
            name.text = "Full name"
        }
    }
}