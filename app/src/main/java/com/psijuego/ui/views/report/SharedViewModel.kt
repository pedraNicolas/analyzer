package com.psijuego.ui.views.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.psijuego.data.model.ui.CategoryUI
import com.psijuego.data.model.ui.HomeUI
import com.psijuego.domain.usecase.CategoryUseCase
import com.psijuego.domain.usecase.ConclusionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val categoryUseCase: CategoryUseCase,
    private val conclusionUseCase: ConclusionUseCase
) : ViewModel() {

    private val _homeUI = MutableLiveData<HomeUI>()
    val homeUI: LiveData<HomeUI> = _homeUI

    private var _categoryUI = MutableLiveData<List<CategoryUI>>()
    val categoryUI: LiveData<List<CategoryUI>> = _categoryUI

    private var _conclusion = MutableLiveData<String>()
    val conclusion: LiveData<String> = _conclusion

    private var _pdfDocument = MutableLiveData<File>()
    val pdfDocument: LiveData<File> = _pdfDocument

    fun setConclusion(conclusion: String){
        _conclusion.postValue(conclusion)
    }

    fun getConclusion(): String?{
        return conclusion.value
    }

    fun setHomeUI(data: HomeUI) {
        _homeUI.postValue(data)
    }

    fun getHomeUI(): HomeUI?{
        return homeUI.value
    }

    fun setCategoryUI(data: List<CategoryUI>) {
        _categoryUI.postValue(data)
    }
    fun getCategoryUI(): List<CategoryUI>?{
        return categoryUI.value
    }

    fun getCategoriesList() {
        viewModelScope.launch {
            val list = categoryUseCase.getCategoriesList()
            if (list.isNotEmpty()) {
                _categoryUI.postValue(list)
            }
        }
    }

    fun uploadDocument(file: File, getUrl:(String) -> Unit){
        conclusionUseCase.uploadDocument(file, getUrl)
    }
}