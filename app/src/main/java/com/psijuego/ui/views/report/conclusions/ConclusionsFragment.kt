package com.psijuego.ui.views.report.conclusions

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.psijuego.R
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
        bindToForm()
        with(binding) {
            btnQR.setOnClickListener { onNavigateToPdf() }
            btnCancel.setOnClickListener { confirmAction() }
            topAppBar.setNavigationOnClickListener { onBack() }
        }
    }

    private fun onNavigateToPdf() {
        bindToObject()
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_conclusionsFragment_to_exportReportFragment)
    }

    private fun bindToObject() {
        val conclusion = binding.tvConclusion.text.toString()
        if (conclusion.isNotEmpty()) {
            viewModel.setConclusion(conclusion)
        }
    }

    private fun bindToForm() {
        if (viewModel.conclusion.value != null) {
            binding.tvConclusion.text =
                Editable.Factory.getInstance().newEditable(viewModel.conclusion.value)
        }
    }

    private fun onCancel() {
        viewModel.setHomeUI(HomeUI())
        viewModel.setConclusion("")
        findNavController().navigate(R.id.action_conclusionsFragment_to_homeFragment)
    }

    private fun onBack() {
        bindToObject()
        findNavController().popBackStack()
    }

    private fun confirmAction() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.confirm_cancel))
            .setMessage(resources.getString(R.string.cancel_supporting_text))
            .setNegativeButton(resources.getString(R.string.cancel)) { _, _ ->
            }
            .setPositiveButton(resources.getString(R.string.accept)) { _, _ ->
                onCancel()
            }
            .show()
    }

    private fun initViewModel() {
        listCategoryUI = viewModel.getCategoryUI() ?: emptyList()
        homeUI = viewModel.getHomeUI() ?: HomeUI()
    }

}