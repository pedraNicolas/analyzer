package com.psijuego.ui.views.report.indicators

interface IndicatorListener {
    fun onItemStateChanged(indicatorUIPosition: Int, parameterPosition: Int, newStatus: Boolean)
    fun onNextClicked()
}