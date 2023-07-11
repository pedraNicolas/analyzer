package com.psijuego.ui.views.report.indicators

interface IndicatorListener {
    fun onItemStateChanged(indicatorUIPosition: Int, parameterName: String, newStatus: Boolean)
    fun onNextClicked()
}