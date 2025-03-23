package com.mahmoud.altasherat.common.presentation

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.Window
import com.mahmoud.altasherat.R
import androidx.core.graphics.drawable.toDrawable

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