package com.psijuego.ui.views.report.indicators.adapter.parameter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.psijuego.core.Constants
import com.psijuego.core.utils.UtilText
import com.psijuego.data.model.ui.ParameterUI
import com.psijuego.databinding.ItemParametersBinding
import com.psijuego.ui.views.report.indicators.CategoryListener

class ParameterRvViewHolder(
    private val binding: ItemParametersBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun render(
        item: ParameterUI,
        listener: CategoryListener?,
        indicatorUIPosition: Int,
        parameterPosition: Int
    ) {
        with(binding) {
            if (item.name != Constants.DESCRIPTION) {

                indicatorName.text = item.name

                if (item.description.isNullOrEmpty()) {
                    indicatorDescription.visibility = View.GONE
                } else {
                    indicatorDescription.text = item.description
                }

                btnState.isChecked = item.selected
                btnState.setOnClickListener {
                    val bool = btnState.isChecked
                    listener?.onItemStateChanged(
                        indicatorUIPosition,
                        item.name,
                        bool
                    )
                }

            }
        }

    }
}
