package com.mahmoud.altasherat.features.menu_options.terms_and_conditions.presentation

import androidx.navigation.fragment.findNavController
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.databinding.FragmentTermsAndConditionsBinding


class TermsAndConditionsFragment : BaseFragment<FragmentTermsAndConditionsBinding>(FragmentTermsAndConditionsBinding::inflate) {

    override fun FragmentTermsAndConditionsBinding.initialize() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()

        }
    }


}