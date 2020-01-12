package com.github.harmittaa.rateconversion.rates

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.harmittaa.rateconversion.R
import com.github.harmittaa.rateconversion.model.SingleRate
import java.math.RoundingMode

interface RateRowListener {
    fun onInputChanged(newInput: Double)
    fun onRowFocused(itemId: Int)
}

interface FocusableListener {
    fun onEditTextFocused(itemId: Int)
}

class RateAdapter(var list: List<SingleRate>) : RecyclerView.Adapter<RateAdapter.ViewHolder>(),
    TextWatcher, FocusableListener {
    lateinit var listener: RateRowListener
    private var currentClickedRow = Int.MAX_VALUE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val row = LayoutInflater.from(parent.context)
            .inflate(R.layout.rate_row, parent, false) as View
        return ViewHolder(row, this, this)
    }

    override fun getItemCount() = list.count()

    override fun getItemId(position: Int): Long {
        return list[position].id.toLong()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setItem(list[position])
    }


    class ViewHolder(row: View, private var listener: TextWatcher, private var focusableListener: FocusableListener) : RecyclerView.ViewHolder(row) {
        private val code: TextView = row.findViewById(R.id.currencyCode)
        private val name: TextView = row.findViewById(R.id.currencyName)
        private val input: EditText = row.findViewById(R.id.currencyInput)
        private var itemId = Int.MIN_VALUE

        fun setItem(item: SingleRate) {
            //input.setText(item.exchangedValue.toBigDecimal().setScale(2, RoundingMode.UP).toDouble().toString())
            itemId = item.id
            code.text = item.code
            name.text = item.currencyName
            input.hint = item.exchangedValue.toBigDecimal().setScale(2, RoundingMode.UP).toDouble().toString()
            input.addTextChangedListener(listener)
            input.setOnFocusChangeListener { _, _ ->
                focusableListener.onEditTextFocused(itemId)
            }
        }
    }

    override fun afterTextChanged(editable: Editable?) {
    }

    override fun onEditTextFocused(itemId: Int) {
        if (currentClickedRow == itemId) return
        currentClickedRow = itemId
        listener.onRowFocused(itemId)
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (p0.isNullOrBlank()) return
        listener.onInputChanged(p0.toString().toDouble())
    }
}