package com.mahmoud.altasherat.features.delete_account

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.databinding.FragmentManageAccountBinding


class ManageAccountFragment : BaseFragment<FragmentManageAccountBinding>(
    FragmentManageAccountBinding::inflate
) {
    override fun FragmentManageAccountBinding.initialize() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(manageAccToolbar)

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
        }
        // Handle back navigation
        manageAccToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        manageAccCard.setOnClickListener {
            findNavController().navigate(R.id.action_manageAccountFragment_to_deleteAccountFragment)
        }
    }
}