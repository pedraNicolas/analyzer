package com.psijuego.ui.views.report.indicators.adapter.parameter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.psijuego.data.model.ui.IndicatorUI
import com.psijuego.data.model.ui.ParameterUI
import com.psijuego.databinding.ItemLayoutBinding
import com.psijuego.ui.views.report.indicators.IndicatorListener

class ParameterRvAdapter(
    private val indicatorUI: IndicatorUI,
    private val indicatorUIPosition: Int
) : RecyclerView.Adapter<ParameterRvViewHolder>() {

    private var listener: IndicatorListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParameterRvViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(layoutInflater, parent, false)
        return ParameterRvViewHolder(binding)
    }

    override fun getItemCount(): Int = indicatorUI.parameter.size
    fun setListener(listener: IndicatorListener) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: ParameterRvViewHolder, position: Int) {
        val item = indicatorUI.parameter[position]
        holder.render(item, listener, indicatorUIPosition, position)
    }
}