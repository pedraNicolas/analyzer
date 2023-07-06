package com.psijuego.ui.views.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.psijuego.data.model.WelcomeUI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor() : ViewModel() {

    private val _welcomeUI = MutableLiveData<WelcomeUI>()
    val welcomeUI: LiveData<WelcomeUI> = _welcomeUI

    fun setWelcomeUI(data: WelcomeUI) {
        _welcomeUI.value = data
    }
}