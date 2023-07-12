package com.psijuego.ui.views.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.psijuego.data.model.ui.CategoryUI
import com.psijuego.data.model.ui.HomeUI
import com.psijuego.domain.usecase.CategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val categoryUseCase: CategoryUseCase
) : ViewModel() {
    private val _homeUI = MutableLiveData<HomeUI>()
    val homeUI: LiveData<HomeUI> = _homeUI

    private var _categoryUI = MutableLiveData<List<CategoryUI>>()
    val categoryUI: LiveData<List<CategoryUI>> = _categoryUI

    private val _categoryUIFinalList = MutableLiveData<List<CategoryUI>>()
    val categoryUIFinalList: LiveData<List<CategoryUI>> = _categoryUIFinalList


    fun setHomeUI(data: HomeUI) {
        _homeUI.value = data
    }

    fun setCategoryUI(data: List<CategoryUI>) {
        _categoryUI.postValue(data)
    }

    fun getCategoriesList() {
        viewModelScope.launch {
            val list = categoryUseCase.getCategoriesList()
            if (list.isNotEmpty()) {
                _categoryUI.postValue(list)
            }
        }


    }
}