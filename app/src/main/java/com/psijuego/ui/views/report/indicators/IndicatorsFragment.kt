package com.psijuego.ui.views.report.indicators

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.psijuego.databinding.FragmentIndicatorsBinding

class IndicatorsFragment : Fragment() {

    private lateinit var binding: FragmentIndicatorsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentIndicatorsBinding.inflate(inflater, container, false)
        return binding.root
    }
}