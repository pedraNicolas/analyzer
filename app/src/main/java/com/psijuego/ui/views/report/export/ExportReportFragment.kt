package com.psijuego.ui.views.report.export

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.psijuego.R
import com.psijuego.core.utils.UtilPDF
import com.psijuego.data.model.ui.CategoryUI
import com.psijuego.data.model.ui.HomeUI
import com.psijuego.databinding.FragmentExportReportBinding
import com.psijuego.ui.views.report.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ExportReportFragment : Fragment() {

    private lateinit var binding: FragmentExportReportBinding
    private val viewModel: SharedViewModel by activityViewModels<SharedViewModel>()
    private lateinit var listCategoryUI: List<CategoryUI>
    private lateinit var homeUI: HomeUI
    private lateinit var conclusion: String
    private lateinit var file: File

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentExportReportBinding.inflate(inflater, container, false)
        setUpViewModel()
        return binding.root
    }

    private fun setUpViewModel() {
        listCategoryUI = viewModel.getCategoryUI() ?: emptyList()
        homeUI = viewModel.getHomeUI() ?: HomeUI()
        conclusion = viewModel.getConclusion() ?: ""
        createPdfDocument()
    }

    private fun createPdfDocument() {
        file = UtilPDF().createPdf(homeUI, listCategoryUI, conclusion)
        viewModel.uploadDocument(file) { url ->
            Log.d("URL", "$url")
        }
        setUpComponent()
    }

    private fun setUpComponent() {
        with(binding) {
            if (file != null && file.exists()) {
                pdfView.fromFile(file).load()
            }
        }
    }

}