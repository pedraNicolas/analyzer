package com.psijuego.ui.views.report.indicators

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.psijuego.R
import com.psijuego.data.model.ui.IndicatorUI
import com.psijuego.data.model.ui.ParameterUI
import com.psijuego.databinding.FragmentIndicatorsBinding
import com.psijuego.ui.views.report.ReportViewModel
import com.psijuego.ui.views.report.indicators.adapter.CirclePagerIndicatorDecoration
import com.psijuego.ui.views.report.indicators.adapter.IndicatorRvAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IndicatorsFragment : Fragment() {

    private lateinit var binding: FragmentIndicatorsBinding
    private val viewModel: ReportViewModel by viewModels()
    private lateinit var indicatorRvAdapter: IndicatorRvAdapter
    private var indicatorList = ArrayList<IndicatorUI>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentIndicatorsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setUpViewModel()
        //setUpComponents()
    }

    private fun setUpViewModel() {
        viewModel.getIndicatorsList()
        viewModel.indicatorUI.observe(viewLifecycleOwner) { list ->
            fillIndicatorList(list)

        }
    }

    private fun fillIndicatorList(list: List<IndicatorUI>) {
        if (list.isNotEmpty()) {
            indicatorList.addAll(list)
            indicatorRvAdapter.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView() {

    }

    private fun setUpRecyclerView() {
        with(binding) {
            rvIndicators.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(rvIndicators)
            indicatorRvAdapter = IndicatorRvAdapter(indicatorList)
            rvIndicators.addItemDecoration(CirclePagerIndicatorDecoration(requireContext()))
            rvIndicators.adapter = indicatorRvAdapter
        }
    }

//    private fun setUpComponents(){
//        binding.btnNext.setOnClickListener{
//            setUpNavigation(it, R.id.action_indicatorsFragment_to_conclusionsFragment)
//        }
//    }

    private fun setUpNavigation(view: View, navigation: Int) {
        Navigation.findNavController(view).navigate(navigation)
    }
}