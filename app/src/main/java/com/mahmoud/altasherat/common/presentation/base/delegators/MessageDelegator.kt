package com.mahmoud.altasherat.common.presentation.base.delegators

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


class MessageDelegator : IMessageDelegator {

    override fun showMessage(message: String, type: MessageType, fragment: Fragment) {
        when (type) {
            MessageType.SNACKBAR -> {
                fragment.view?.let { view ->
                    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
                }
            }

            MessageType.DIALOG -> {
                val dialog = AlertDialog.Builder(fragment.requireContext())
                    .setMessage(message)
                    .setPositiveButton("OK", null)
                    .create()
                dialog.show()
            }

            MessageType.TOAST -> {
                Toast.makeText(fragment.requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}