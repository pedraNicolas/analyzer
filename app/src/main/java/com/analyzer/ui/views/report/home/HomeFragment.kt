package com.analyzer.ui.views.report.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.analyzer.R
import com.analyzer.data.model.WelcomeUI
import com.analyzer.databinding.FragmentHomeBinding
import com.analyzer.databinding.FragmentWelcomeBinding
import com.analyzer.ui.views.report.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var welcomeUI: WelcomeUI

    private val viewModel: ReportViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setUpComponents()
        return binding.root
    }

    private fun setUpComponents() {
        with(binding) {
            btnNext.setOnClickListener {
                viewModel.setWelcomeUI(bindToObject())
                Navigation.findNavController(it)
                    .navigate(R.id.action_homeFragment_to_indicatorsFragment)
            }
        }
    }

    private fun bindToObject(): WelcomeUI {
        with(binding) {
            tvProfessionalName.text?.toString()?.let { welcomeUI.nameProfessional = it }
            tvPatientName.text?.toString()?.let { welcomeUI.namePatient = it }
            tvRegistrationNumber.text?.toString()?.let { welcomeUI.numberRegistration = it }
        }
        return welcomeUI
    }
}