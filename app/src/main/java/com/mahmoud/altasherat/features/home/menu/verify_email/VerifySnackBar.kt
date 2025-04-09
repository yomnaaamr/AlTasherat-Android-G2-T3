package com.mahmoud.altasherat.features.home.menu.verify_email

import android.annotation.SuppressLint
import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.mahmoud.altasherat.R

class VerifySnackBar {
    companion object {
        @SuppressLint("RestrictedApi")
        fun showVerificationSnackbar(
            activity: Activity,
            message: String,
            verifyActions: VerifyActions?
        ) {
            val rootView = activity.findViewById<View>(android.R.id.content)
            val snackbar = Snackbar.make(rootView, "", Snackbar.LENGTH_INDEFINITE)

            // Inflate layout
            val layoutInflater = LayoutInflater.from(activity)
            val customView = layoutInflater.inflate(R.layout.verify_email_snackbar_layout, null)

            // Setup views
            val messageText = customView.findViewById<TextView>(R.id.verify_message)
            val confirmBtn = customView.findViewById<MaterialButton>(R.id.confirm_verify_btn)
            val closeBtn = customView.findViewById<CardView>(R.id.close_icon)

            messageText.text = message

            if (verifyActions is VerifyActions.OnConfirmClickListener) {
                confirmBtn.visibility = View.VISIBLE
                confirmBtn.setOnClickListener {
                    verifyActions.onConfirmClicked()
                    snackbar.dismiss()
                }
            }
            if (verifyActions is VerifyActions.OnCloseClickListener) {
                closeBtn.visibility = View.VISIBLE
                closeBtn.setOnClickListener {
                    verifyActions.onCloseClicked()
                    snackbar.dismiss()
                }
            }

            // Replace default layout
            val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
            snackbarLayout.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)?.visibility =
                View.INVISIBLE
            snackbarLayout.setPadding(0, 0, 0, 0)
            snackbarLayout.addView(customView, 0)

            // Set position and margin
            val params = snackbar.view.layoutParams as FrameLayout.LayoutParams
            params.gravity = Gravity.TOP
            params.setMargins(16, 100, 16, 0)
            snackbar.view.layoutParams = params

            snackbar.show()
        }
    }
}