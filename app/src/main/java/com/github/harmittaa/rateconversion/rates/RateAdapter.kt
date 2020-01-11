package com.github.harmittaa.rateconversion.rates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.harmittaa.rateconversion.R

class RateAdapter(var list: List<String>) : RecyclerView.Adapter<RateAdapter.ViewHolder>() {


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
        private val name: TextView = item.findViewById(R.id.currencyName)
        fun setItem(item: String) {
            name.text = item
        }
    }
}