package com.psijuego.ui.views.report.indicators

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.psijuego.R
import com.psijuego.databinding.FragmentIndicatorsBinding
import com.psijuego.ui.views.report.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IndicatorsFragment : Fragment() {

    private lateinit var binding: FragmentIndicatorsBinding
    private val viewModel: ReportViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentIndicatorsBinding.inflate(inflater, container, false)
        setUpComponents()
        setUpViewModel()
        return binding.root
    }

    private fun setUpViewModel() {
        viewModel.getIndicatorsList()
    }

    private fun setUpComponents(){
        binding.btnNext.setOnClickListener{
            setUpNavigation(it)
        }
    }
    
    private fun setUpNavigation(view:View){
        Navigation.findNavController(view).navigate(R.id.action_indicatorsFragment_to_conclusionsFragment)
    }
}