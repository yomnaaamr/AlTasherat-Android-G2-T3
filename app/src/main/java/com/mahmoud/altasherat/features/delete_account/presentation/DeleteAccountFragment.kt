package com.mahmoud.altasherat.features.delete_account.presentation

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.databinding.FragmentDeleteAccountBinding

class DeleteAccountFragment : BaseFragment<FragmentDeleteAccountBinding>(
    FragmentDeleteAccountBinding::inflate
) {
    private lateinit var confirmBottomSheet: ConfirmDeleteBottomSheet

    override fun FragmentDeleteAccountBinding.initialize() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(deleteAccToolbar)

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
        }
        // Handle back navigation
        deleteAccToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        confirmDeleteAccBtn.setOnClickListener {
            confirmBottomSheet = ConfirmDeleteBottomSheet()
            confirmBottomSheet.show(childFragmentManager, "Confirm delete account bottom sheet")
        }
    }
}