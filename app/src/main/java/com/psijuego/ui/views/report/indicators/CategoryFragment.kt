package com.psijuego.ui.views.report.indicators

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.psijuego.R
import com.psijuego.core.Constants
import com.psijuego.data.model.ui.CategoryUI
import com.psijuego.databinding.FragmentCategoryBinding
import com.psijuego.ui.views.report.SharedViewModel
import com.psijuego.core.components.CirclePagerIndicatorDecoration
import com.psijuego.core.utils.ResourceState
import com.psijuego.ui.views.report.indicators.adapter.category.CategoryRvAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment(), CategoryListener {

    private lateinit var binding: FragmentCategoryBinding
    private val viewModel: SharedViewModel by activityViewModels<SharedViewModel>()
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
        setUpComponents()
        setUpViewModel()
        setUpRecyclerView()
    }

    private fun setUpComponents() {
        with(binding) {
            topAppBar.setNavigationOnClickListener { onBack() }
            noConnection.retryButton?.setOnClickListener {
                noConnection.visibility = View.GONE
                rvCategory.visibility = View.VISIBLE
                viewModel.getCategoriesList()
            }
        }
    }

    private fun setUpViewModel() {
        if (categoriesList.isNullOrEmpty()) {
            viewModel.getCategoriesList()
            setUpObservers()
        }
    }

    private fun setUpObservers() {
        viewModel.dataState.observe(viewLifecycleOwner) { resourceState ->
            with(binding) {
                when (resourceState) {
                    is ResourceState.Loading -> {
                        pbLoading.visibility = View.VISIBLE
                    }

                    is ResourceState.Success -> {
                        pbLoading.visibility = View.GONE
                        fillIndicatorList(resourceState.data)
                    }

                    is ResourceState.Failure -> {
                        pbLoading.visibility = View.GONE
                        if (resourceState.message.contains(Constants.NOT_CONNECTION)) {
                            noConnection.visibility = View.VISIBLE
                            rvCategory.visibility = View.GONE
                        }
                    }
                }
            }
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
        viewModel.dataState.removeObservers(viewLifecycleOwner)
        setUpNavigation(binding.root, R.id.action_indicatorsFragment_to_conclusionsFragment)
    }

    private fun bindToObject() {
        viewModel.setCategoryUI(categoriesList)
    }

    private fun onBack() {
        bindToObject()
        findNavController().popBackStack()
    }
}