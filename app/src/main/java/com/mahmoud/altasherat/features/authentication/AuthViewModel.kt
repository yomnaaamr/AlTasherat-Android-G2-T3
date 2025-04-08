package com.mahmoud.altasherat.features.authentication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel: ViewModel() {

    val switchTabLiveData = MutableLiveData<Int>()

    fun switchToTab(tabIndex: Int) {
        switchTabLiveData.value = tabIndex
    }
}