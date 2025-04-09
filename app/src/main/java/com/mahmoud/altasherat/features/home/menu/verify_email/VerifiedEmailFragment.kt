package com.mahmoud.altasherat.features.home.menu.verify_email

import androidx.navigation.fragment.findNavController
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.databinding.FragmentVerifiedEmailBinding

class VerifiedEmailFragment : BaseFragment<FragmentVerifiedEmailBinding>(
    FragmentVerifiedEmailBinding::inflate
) {

    override fun FragmentVerifiedEmailBinding.initialize() {
        skipBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}