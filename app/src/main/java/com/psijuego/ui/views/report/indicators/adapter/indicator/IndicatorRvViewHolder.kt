package com.psijuego.ui.views.report.indicators.adapter.indicator

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.psijuego.core.Constants
import com.psijuego.core.utils.UtilText
import com.psijuego.data.model.ui.IndicatorUI
import com.psijuego.databinding.IndicatorLayoutBinding
import com.psijuego.ui.views.report.indicators.IndicatorListener
import com.psijuego.ui.views.report.indicators.IndicatorsFragment
import com.psijuego.ui.views.report.indicators.adapter.parameter.ParameterRvAdapter

class IndicatorRvViewHolder(
    private val binding: IndicatorLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var parameterRvAdapter: ParameterRvAdapter

    fun render(
        item: IndicatorUI,
        indicatorUIPosition: Int,
        size: Int,
        listener: IndicatorListener?
    ) {
        with(binding) {
            val maxLength = 20
            headerLayout.setTitle(UtilText.formatTextMaxCharactersPerLine(item.name, maxLength))
            item.parameter.let {
                for (parameter in it) {
                    if (parameter.name == Constants.DESCRIPTION) {
                        if (parameter.description != "") {
                            tvDescription.visibility = View.VISIBLE
                            tvDescription.text = parameter.description
                        } else {
                            tvDescription.visibility = View.GONE
                        }

                    }
                }
            }

            headerLayout.showBack(position == 0)
            btnNext.visibility = if (size - 1 == position) View.VISIBLE else View.GONE
            btnNext.setOnClickListener {
                listener?.onNextClicked()
            }
            rvParameters.layoutManager =
                LinearLayoutManager(rvParameters.context, LinearLayoutManager.VERTICAL, false)
            parameterRvAdapter = ParameterRvAdapter(item, indicatorUIPosition)
            if (listener != null) {
                parameterRvAdapter.setListener(listener)
            }
            rvParameters.adapter = parameterRvAdapter

        }
    }


}