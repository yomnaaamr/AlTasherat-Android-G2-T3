package com.mahmoud.altasherat.features.delete_account.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mahmoud.altasherat.databinding.BottomSheetDeleteAccBinding

class ConfirmDeleteBottomSheet : BottomSheetDialogFragment() {
    private lateinit var bottomSheetDeleteAccBinding: BottomSheetDeleteAccBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bottomSheetDeleteAccBinding =
            BottomSheetDeleteAccBinding.inflate(inflater, container, false)

        bottomSheetDeleteAccBinding.confirmDeleteAccBtn.setOnClickListener {
            deleteAccount()
        }
        bottomSheetDeleteAccBinding.cancelDeleteAccBtn.setOnClickListener {
            dismiss()
        }

        return bottomSheetDeleteAccBinding.root
    }

    private fun deleteAccount() {
        val password = bottomSheetDeleteAccBinding.passwordEdit.text.toString()
        if (password.isNotEmpty()) {

        }
    }
}