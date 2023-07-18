package com.psijuego.ui.views.report.conclusions

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.psijuego.R
import com.psijuego.core.utils.UtilPDF
import com.psijuego.data.model.ui.CategoryUI
import com.psijuego.data.model.ui.HomeUI
import com.psijuego.databinding.FragmentConclusionsBinding
import com.psijuego.ui.views.report.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConclusionsFragment : Fragment() {

    private lateinit var binding: FragmentConclusionsBinding
    private val viewModel: SharedViewModel by activityViewModels<SharedViewModel>()
    private lateinit var listCategoryUI: List<CategoryUI>
    private lateinit var homeUI: HomeUI
    private var documentReference = Firebase.storage.reference

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
                viewModel.setConclusion(tvConclusion.text.toString())
                navigateToPdfView()
            }
        }
    }

    private fun navigateToPdfView() {
        Navigation.findNavController(binding.root).navigate(R.id.action_conclusionsFragment_to_exportReportFragment)
    }

    private fun initViewModel() {
        listCategoryUI = viewModel.getCategoryUI() ?: emptyList()
        homeUI = viewModel.getHomeUI() ?: HomeUI()
    }


}