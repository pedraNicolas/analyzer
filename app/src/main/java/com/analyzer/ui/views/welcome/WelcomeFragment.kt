package com.analyzer.ui.views.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.R
import com.example.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)



        setUpComponents()


        return binding.root
    }

    private fun setUpComponents() {
        with(binding) {
            btnStart.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.action_welcomeFragment_to_homeFragment)
            }
        }
    }
}