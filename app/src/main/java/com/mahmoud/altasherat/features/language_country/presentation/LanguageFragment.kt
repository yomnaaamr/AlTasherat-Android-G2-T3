package com.mahmoud.altasherat.features.language_country.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.databinding.FragmentLanguageBinding
import com.mahmoud.altasherat.databinding.FragmentOnBoardingBinding
import com.mahmoud.altasherat.features.onBoarding.presentation.viewModel.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LanguageFragment : Fragment() {

    private lateinit var binding: FragmentLanguageBinding
    private val languageViewModel: LanguageViewModel by viewModels()
    private val onBoardingViewModel: OnBoardingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.continueBtn.setOnClickListener {

        }
    }
}