package com.psijuego.ui.views.report.indicators

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.psijuego.R
import com.psijuego.databinding.FragmentIndicatorsBinding

class IndicatorsFragment : Fragment() {

    private lateinit var binding: FragmentIndicatorsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentIndicatorsBinding.inflate(inflater, container, false)
        setUpComponents()
        return binding.root
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