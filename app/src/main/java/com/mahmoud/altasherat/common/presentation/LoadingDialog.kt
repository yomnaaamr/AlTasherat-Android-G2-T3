package com.mahmoud.altasherat.common.presentation

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.core.graphics.drawable.toDrawable
import com.mahmoud.altasherat.R

class LoadingDialog(private val context: Context) {

    private var dialog: Dialog? = null

    fun show() {
        if (dialog == null) {
            dialog = Dialog(context).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(R.layout.dialog_loading)
                setCancelable(false)
                window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            }
        }
        dialog?.show()
    }

    fun dismiss() {
        dialog?.dismiss()
    }
}