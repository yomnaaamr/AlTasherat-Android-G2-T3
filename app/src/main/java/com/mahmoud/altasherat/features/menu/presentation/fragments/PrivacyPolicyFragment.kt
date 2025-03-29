package com.mahmoud.altasherat.features.menu.presentation.fragments

import androidx.navigation.fragment.findNavController
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.databinding.FragmentPrivacyPolicyBinding


class PrivacyPolicyFragment : BaseFragment<FragmentPrivacyPolicyBinding>(FragmentPrivacyPolicyBinding::inflate) {

    override fun FragmentPrivacyPolicyBinding.initialize() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }


}