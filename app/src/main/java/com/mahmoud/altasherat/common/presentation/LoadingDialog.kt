package com.mahmoud.altasherat.common.presentation

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.mahmoud.solutionx.R

class LoadingDialog {
    private var dialog: Dialog? = null

    fun show(context: Context) {
        if (dialog == null) {
            dialog = Dialog(context).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(R.layout.dialog_loading)
                setCancelable(false)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
        dialog?.show()
    }

    fun dismiss() {
        dialog?.dismiss()
    }
}