package com.psijuego.domain.usecase

import com.psijuego.data.model.ui.IndicatorUI
import com.psijuego.data.repositories.IndicatorsRepository
import javax.inject.Inject

class IndicatorsUseCase @Inject constructor(
    private val indicatorsRepository: IndicatorsRepository
) {
    suspend fun getIndicatorsList(): List<IndicatorUI> {
        return indicatorsRepository.getIndicatorsList()
    }
}