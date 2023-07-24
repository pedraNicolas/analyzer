package com.psijuego.ui.views.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.psijuego.core.utils.ResourceState
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

    private var _pdfStorageUrl = MutableLiveData<String>()
    val pdfStorageUrl: LiveData<String> = _pdfStorageUrl

    fun setConclusion(conclusion: String) {
        _conclusion.postValue(conclusion)
    }

    fun getConclusion(): String? {
        return conclusion.value
    }

    fun setHomeUI(data: HomeUI) {
        _homeUI.postValue(data)
    }

    fun getHomeUI(): HomeUI? {
        return homeUI.value
    }

    fun setCategoryUI(data: List<CategoryUI>) {
        _categoryUI.postValue(data)
    }

    fun getCategoryUI(): List<CategoryUI>? {
        return categoryUI.value
    }

    private val _dataState: MutableLiveData<ResourceState<List<CategoryUI>>> = MutableLiveData()
    val dataState: LiveData<ResourceState<List<CategoryUI>>> get() = _dataState

    fun getCategoriesList() {
        _dataState.value = ResourceState.Loading
        viewModelScope.launch {
            try {
                val list = categoryUseCase.getCategoriesList()
                if (list.isNotEmpty()) {
                    _dataState.value = ResourceState.Success(list)
                    _categoryUI.postValue(list)
                } else {
                    _dataState.value = ResourceState.Failure("No se encontraron categorías.")
                }
            } catch (e: Exception) {
                _dataState.value =
                    ResourceState.Failure("Error al cargar las categorías: ${e.message}")
            }
        }
    }

    fun uploadDocument(file: File) {
        conclusionUseCase.uploadDocument(file) {
            _pdfStorageUrl.postValue(it)
        }
    }
}