package com.psijuego.ui.views.report

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.psijuego.core.utils.CoreModule
import com.psijuego.data.model.ui.WelcomeUI
import com.psijuego.domain.usecase.IndicatorsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val indicatorsUseCase: IndicatorsUseCase
) : ViewModel() {

    private val _welcomeUI = MutableLiveData<WelcomeUI>()
    val welcomeUI: LiveData<WelcomeUI> = _welcomeUI

    fun setWelcomeUI(data: WelcomeUI) {
        _welcomeUI.value = data
    }

    fun getIndicatorsList(){
        viewModelScope.launch {
            val list = indicatorsUseCase.getIndicatorsList()
            if(list.isNotEmpty()){
                Toast.makeText(CoreModule.getContext(), "Se descargo", Toast.LENGTH_SHORT)
            }
        }
    }
}