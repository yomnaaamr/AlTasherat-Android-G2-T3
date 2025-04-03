package com.mahmoud.altasherat.features.menu.presentation.fragments

import androidx.navigation.fragment.findNavController
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.databinding.FragmentContactUsBinding


class ContactUsFragment : BaseFragment<FragmentContactUsBinding>(FragmentContactUsBinding::inflate) {


    override fun FragmentContactUsBinding.initialize() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}