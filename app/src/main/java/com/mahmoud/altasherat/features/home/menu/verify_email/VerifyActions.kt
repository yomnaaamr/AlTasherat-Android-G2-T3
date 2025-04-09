package com.mahmoud.altasherat.features.home.menu.verify_email

sealed interface VerifyActions {
    interface OnConfirmClickListener : VerifyActions {
        fun onConfirmClicked()
    }

    interface OnCloseClickListener : VerifyActions {
        fun onCloseClicked()
    }
}