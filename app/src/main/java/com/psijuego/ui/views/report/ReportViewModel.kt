package com.psijuego.ui.views.report

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.psijuego.core.utils.CoreModule
import com.psijuego.data.model.ui.IndicatorUI
import com.psijuego.data.model.ui.WelcomeUI
import com.psijuego.domain.usecase.IndicatorsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val indicatorsUseCase: IndicatorsUseCase
) : ViewModel() {
    private val _welcomeUI = MutableLiveData<WelcomeUI>()
    val welcomeUI: LiveData<WelcomeUI> = _welcomeUI

    private var _indicatorUI = MutableLiveData<List<IndicatorUI>>()
    val indicatorUI: LiveData<List<IndicatorUI>> = _indicatorUI

    private val _indicatorUIFinalList = MutableLiveData<List<IndicatorUI>>()
    val indicatorUIFinalList: LiveData<List<IndicatorUI>> = _indicatorUIFinalList


    fun setWelcomeUI(data: WelcomeUI) {
        _welcomeUI.value = data
    }

    fun setIndicatorUI(data: List<IndicatorUI>) {
        _indicatorUIFinalList.postValue(data)
    }

    fun getIndicatorsList() {
        if (_indicatorUIFinalList.value.isNullOrEmpty()) {
            viewModelScope.launch {
                val list = indicatorsUseCase.getIndicatorsList()
                if (list.isNotEmpty()) {
                    _indicatorUI.postValue(list)
                }
            }
        } else {
            _indicatorUI = _indicatorUIFinalList
        }

    }
}