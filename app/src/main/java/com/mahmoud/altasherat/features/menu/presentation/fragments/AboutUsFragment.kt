package com.mahmoud.altasherat.features.menu.presentation.fragments

import androidx.navigation.fragment.findNavController
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.databinding.FragmentAboutUsBinding


class AboutUsFragment : BaseFragment<FragmentAboutUsBinding>(FragmentAboutUsBinding::inflate) {


    override fun FragmentAboutUsBinding.initialize() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }


}