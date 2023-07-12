package com.psijuego.ui.views.report.indicators

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.psijuego.R
import com.psijuego.data.model.ui.CategoryUI
import com.psijuego.databinding.FragmentCategoryBinding
import com.psijuego.ui.views.report.ReportViewModel
import com.psijuego.ui.views.report.indicators.adapter.category.CirclePagerIndicatorDecoration
import com.psijuego.ui.views.report.indicators.adapter.category.CategoryRvAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment(), CategoryListener {

    private lateinit var binding: FragmentCategoryBinding
    private val viewModel: ReportViewModel by viewModels()
    private lateinit var categoryRvAdapter: CategoryRvAdapter
    private var categoriesList = listOf<CategoryUI>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        setUpRecyclerView()
    }

    private fun setUpViewModel() {
        if(categoriesList.isNullOrEmpty()) viewModel.getCategoriesList()
        viewModel.categoryUI.observe(viewLifecycleOwner) { list ->
            fillIndicatorList(list)
        }
    }

    private fun fillIndicatorList(list: List<CategoryUI>) {
        if (list.isNotEmpty()) {
            categoriesList = list
            categoryRvAdapter.updateList(list)
        }
    }

    private fun setUpRecyclerView() {
        with(binding) {
            rvCategory.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(rvCategory)
            categoryRvAdapter = CategoryRvAdapter(categoriesList)
            categoryRvAdapter.setListener(this@CategoryFragment)
            rvCategory.addItemDecoration(CirclePagerIndicatorDecoration(requireContext()))
            rvCategory.adapter = categoryRvAdapter
        }
    }

    private fun setUpNavigation(view: View, navigation: Int) {
        Navigation.findNavController(view).navigate(navigation)
    }

    override fun onItemStateChanged(
        indicatorUIPosition: Int,
        parameterName: String,
        newStatus: Boolean
    ) {
        categoriesList[indicatorUIPosition].parameter.forEach { element ->
            if (element.name == parameterName) {
                element.selected = newStatus
            }
        }
        binding.rvCategory.scrollToPosition(indicatorUIPosition)
        categoryRvAdapter.updateList(categoriesList)
    }

    override fun onNextClicked() {
        bindToObject()
        viewModel.categoryUI.removeObservers(viewLifecycleOwner)
        setUpNavigation(binding.root, R.id.action_indicatorsFragment_to_conclusionsFragment)
    }

    private fun bindToObject() {
        viewModel.setCategoryUI(categoriesList)
    }
}