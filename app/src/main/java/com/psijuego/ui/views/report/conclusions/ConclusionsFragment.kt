package com.psijuego.ui.views.report.conclusions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.psijuego.core.utils.UtilPDF
import com.psijuego.data.model.ui.CategoryUI
import com.psijuego.data.model.ui.HomeUI
import com.psijuego.databinding.FragmentConclusionsBinding
import com.psijuego.ui.views.report.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConclusionsFragment : Fragment() {

    private lateinit var binding: FragmentConclusionsBinding
    private val viewModel: ReportViewModel by activityViewModels<ReportViewModel>()
    private lateinit var listCategoryUI: List<CategoryUI>
    private lateinit var homeUI: HomeUI
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentConclusionsBinding.inflate(inflater, container, false)
        initViewModel()
        setUpComponents()
        return binding.root
    }

    private fun setUpComponents() {
        with(binding) {
            btnQR.setOnClickListener {
                val conclusion = tvConclusion.text.toString()
                createPdfDocument(conclusion)
            }
        }
    }

    private fun initViewModel() {
        listCategoryUI = viewModel.getCategoryUI() ?: emptyList()
        homeUI = viewModel.getHomeUI() ?: HomeUI()
    }

    private fun createPdfDocument(conclusion: String) {
        UtilPDF(homeUI, listCategoryUI, conclusion)
    }

}