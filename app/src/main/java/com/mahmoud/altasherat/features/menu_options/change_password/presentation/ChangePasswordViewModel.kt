package com.mahmoud.altasherat.features.menu_options.change_password.presentation

import androidx.lifecycle.ViewModel
import com.mahmoud.altasherat.features.change_password.domain.usecase.ChangePasswordUC
import javax.inject.Inject

class ChangePasswordViewModel @Inject constructor(
    private val changePassUC: ChangePasswordUC
) : ViewModel() {

}