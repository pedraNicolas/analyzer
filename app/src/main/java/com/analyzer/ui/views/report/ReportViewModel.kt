package com.analyzer.ui.views.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.analyzer.data.model.WelcomeUI
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class ReportViewModel : ViewModel() {

    private val _welcomeUI = MutableLiveData<WelcomeUI>()
    val welcomeUI: LiveData<WelcomeUI> = _welcomeUI

    fun setWelcomeUI(data: WelcomeUI) {
        _welcomeUI.value = data
    }
}