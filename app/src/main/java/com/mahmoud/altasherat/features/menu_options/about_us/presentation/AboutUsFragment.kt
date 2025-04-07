package com.mahmoud.altasherat.features.menu_options.about_us.presentation

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