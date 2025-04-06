package com.mahmoud.altasherat.features.delete_account.delete_acc.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.databinding.BottomSheetDeleteAccBinding

class ConfirmDeleteBottomSheet(
    private val onConfirmClicked: (String) -> Unit
) : BottomSheetDialogFragment() {
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
        bottomSheetDeleteAccBinding.passwordEdit.addTextChangedListener { it ->
            if (it.isNullOrEmpty()) {
                showValidationError()
            } else {
                hideValidationError()
            }
        }

        return bottomSheetDeleteAccBinding.root
    }

    private fun deleteAccount() {
        val password = bottomSheetDeleteAccBinding.passwordEdit.text.toString()
        val value = getValidatedInput(password)
        if (value != null) {
            onConfirmClicked(value)
            dismiss()
        }
    }

    private fun getValidatedInput(text: String): String? {
        return if (text.isEmpty()) {
            showValidationError()
            null
        } else {
            hideValidationError()
            text
        }
    }

    private fun showValidationError() {
        bottomSheetDeleteAccBinding.passwordLayout.isErrorEnabled = true
        bottomSheetDeleteAccBinding.passwordLayout.errorIconDrawable = null
        bottomSheetDeleteAccBinding.passwordLayout.error =
            resources.getString(R.string.password_required)
    }

    private fun hideValidationError() {
        bottomSheetDeleteAccBinding.passwordLayout.isErrorEnabled = false
        bottomSheetDeleteAccBinding.passwordLayout.errorIconDrawable = null
        bottomSheetDeleteAccBinding.passwordLayout.error = null
    }
}