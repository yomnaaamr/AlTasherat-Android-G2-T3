package com.mahmoud.altasherat.features.profile_info.presentation

import androidx.lifecycle.ViewModel
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.usecase.GetUserInfoUC

class ProfileInfoViewModel(
    private val getUserInfoUC: GetUserInfoUC
) : ViewModel() {

}