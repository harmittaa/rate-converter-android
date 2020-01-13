package com.github.harmittaa.rateconversion.rates

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.harmittaa.rateconversion.R
import com.github.harmittaa.rateconversion.model.SingleRate
import com.github.harmittaa.rateconversion.rates.view.Flag
import java.math.RoundingMode

class RateAdapter(var list: List<SingleRate>) : RecyclerView.Adapter<RateAdapter.ViewHolder>(),
    TextWatcher {
    lateinit var listener: RateRowListener
    private var selectedRowId = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val row = LayoutInflater.from(parent.context)
            .inflate(R.layout.rate_row, parent, false) as View
        return ViewHolder(row, this)
    }

    fun onRowClicked(itemId: Int) {
        if (selectedRowId == itemId) return
        selectedRowId = itemId
        listener.onRowFocused(itemId)
    }

    override fun onTextChanged(newInput: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (newInput.isNullOrBlank()) return
        val input: Double = try {
            newInput.toString().toDouble()
        } catch (e: NumberFormatException) {
            0.0
        }
        listener.onInputChanged(input)
    }

    override fun getItemCount() = list.count()
    override fun getItemId(position: Int) = list[position].id.toLong()
    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.setItem(list[position])

    override fun afterTextChanged(editable: Editable?) {}
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    inner class ViewHolder(row: View, private var listener: TextWatcher) :
        RecyclerView.ViewHolder(row) {
        private val code: TextView = row.findViewById(R.id.currencyCode)
        private val name: TextView = row.findViewById(R.id.currencyName)
        private val input: EditText = row.findViewById(R.id.currencyInput)
        private val flag: ImageView = row.findViewById(R.id.currencyFlag)
        private var itemId = Int.MIN_VALUE

        fun setItem(item: SingleRate) {
            itemId = item.id
            flag.clipToOutline = true
            flag.setImageDrawable(itemView.context.getDrawable(Flag.valueOf(item.code).resourceId))
            code.text = item.code
            name.text = item.currencyName
            input.hint = item.exchangedValue.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                .toString()

            // listen to changes only on the first position
            // allows input in other positions, but doesn't react != good UX
            // to be done in ticket-N+1
            if (isFirstRow()) {
                input.addTextChangedListener(listener)
            } else {
                input.removeTextChangedListener(listener)
                input.text.clear()
                input.clearFocus()
            }

            itemView.setOnClickListener {
                onRowClicked(itemId)
                input.setText(input.hint.toString())
                input.requestFocus()
            }
        }

        private fun isFirstRow() = adapterPosition == 0 && itemId == selectedRowId
    }
}