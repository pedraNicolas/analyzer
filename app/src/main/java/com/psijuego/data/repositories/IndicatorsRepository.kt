package com.psijuego.data.repositories

import com.psijuego.data.model.ui.IndicatorUI
import com.psijuego.data.model.ui.ParameterUI
import com.psijuego.data.network.DatabaseFirestore
import javax.inject.Inject

class IndicatorsRepository @Inject constructor(
    private val db: DatabaseFirestore
) {
    suspend fun getIndicatorsList(): List<IndicatorUI> {
        val indicatorList = db.getDataFromFirestore().map { document ->
            val indicator = document.id
            val parameterList = document.data?.entries?.mapNotNull {
                ParameterUI(it.key, it.value.toString(), false)
            } ?: emptyList()

            IndicatorUI(indicator, parameterList)
        }
        return indicatorList
    }
}