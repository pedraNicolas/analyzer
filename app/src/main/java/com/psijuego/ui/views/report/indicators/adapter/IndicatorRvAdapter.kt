package com.psijuego.ui.views.report.indicators.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.psijuego.data.model.ui.IndicatorUI
import com.psijuego.data.model.ui.ParameterUI
import com.psijuego.databinding.IndicatorLayoutBinding
import com.psijuego.databinding.ItemLayoutBinding

class IndicatorRvAdapter(
    private val indicatorUIList: List<IndicatorUI>
) : RecyclerView.Adapter<IndicatorRvViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndicatorRvViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = IndicatorLayoutBinding.inflate(layoutInflater, parent, false)
        return IndicatorRvViewHolder(binding)
    }

    override fun getItemCount(): Int = indicatorUIList.size

    override fun onBindViewHolder(holder: IndicatorRvViewHolder, position: Int) {
        val item = indicatorUIList[position]
        holder.render(item, position, itemCount)
    }
}