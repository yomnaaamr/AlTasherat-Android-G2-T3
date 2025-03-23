package com.mahmoud.altasherat.common.presentation.base.delegators

import androidx.fragment.app.Fragment

interface IMessageDelegator {
    fun showMessage(message: String, type: MessageType, fragment: Fragment)
}

enum class MessageType {
    SNACKBAR,
    DIALOG,
    TOAST
}
