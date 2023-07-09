package com.psijuego.ui.views.report.indicators.adapter.parameter

import androidx.recyclerview.widget.RecyclerView
import com.psijuego.core.Constants
import com.psijuego.core.utils.UtilText
import com.psijuego.data.model.ui.ParameterUI
import com.psijuego.databinding.ItemLayoutBinding
import com.psijuego.ui.views.report.indicators.IndicatorListener

class ParameterRvViewHolder(
    private val binding: ItemLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun render(
        item: ParameterUI,
        listener: IndicatorListener?,
        indicatorUIPosition: Int,
        parameterPosition: Int
    ) {
        with(binding) {
            if(item.name != Constants.DESCRIPTION){
                indicatorName.text = UtilText.formatTextMaxCharactersPerLine(item.name, 23)
                listener?.onItemStateChanged(
                    indicatorUIPosition,
                    parameterPosition,
                    btnState.isChecked
                )
            }
        }

    }
}
