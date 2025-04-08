package com.mahmoud.altasherat.features.authentication.reset_password.presentation

import androidx.navigation.fragment.findNavController
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.databinding.FragmentResetPass1Binding


class ResetPassFragment1 :
    BaseFragment<FragmentResetPass1Binding>(FragmentResetPass1Binding::inflate) {

    override fun FragmentResetPass1Binding.initialize() {
        binding.continueBtn.setOnClickListener {
            findNavController().navigate(R.id.action_resetPassFragment1_to_resetPassFragment2)
        }
    }


}