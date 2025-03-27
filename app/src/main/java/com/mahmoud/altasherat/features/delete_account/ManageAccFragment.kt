package com.mahmoud.altasherat.features.delete_account

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.databinding.FragmentManageAccBinding

class ManageAccFragment :
    BaseFragment<FragmentManageAccBinding>(FragmentManageAccBinding::inflate) {


    override fun FragmentManageAccBinding.initialize() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
        }
        // Handle back navigation
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        manageAccBtn.setOnClickListener {
            findNavController().navigate(R.id.action_manageAccFragment_to_deleteAccountFragment)
        }
    }

}