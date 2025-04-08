package com.mahmoud.altasherat.features.authentication.reset_password.presentation

import androidx.navigation.fragment.findNavController
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.databinding.FragmentResetPassword3Binding

class ResetPasswordFragment3 : BaseFragment<FragmentResetPassword3Binding>(FragmentResetPassword3Binding::inflate)  {

    override fun FragmentResetPassword3Binding.initialize() {

        binding.continueBtn.setOnClickListener {
            findNavController().navigate(R.id.action_resetPasswordFragment3_to_home_nav_graph)
        }

    }


}