package com.psijuego.ui.views.report.conclusions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.psijuego.R
import com.psijuego.databinding.FragmentConclusionsBinding

class ConclusionsFragment : Fragment() {

    private lateinit var binding:FragmentConclusionsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentConclusionsBinding.inflate(inflater, container, false)
        return binding.root
    }

}