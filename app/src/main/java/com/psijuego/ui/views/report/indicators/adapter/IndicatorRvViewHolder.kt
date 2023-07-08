package com.psijuego.ui.views.report.indicators.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.psijuego.core.Constants
import com.psijuego.core.utils.UtilText
import com.psijuego.data.model.ui.IndicatorUI
import com.psijuego.databinding.IndicatorLayoutBinding

class IndicatorRvViewHolder(
    private val binding: IndicatorLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun render(item: IndicatorUI, position: Int, size: Int) {
        with(binding) {
            val maxLength = 20
            headerLayout.setTitle(UtilText.formatTextMaxCharactersPerLine(item.name, maxLength))
            item.parameter.let {
                for (parameter in it) {
                    if (parameter.name == Constants.DESCRIPTION) {
                        tvDescription.text = parameter.description
                    }
                }
            }

            headerLayout.showBack(position == 0)
            btnNext.visibility = if (size-1 == position) View.VISIBLE else View.GONE

        }
    }


}