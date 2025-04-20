package com.mahmoud.altasherat.features.update_account.domain.models

import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.models.User

data class UpdateAcc(
    val message: String,
    val user: User
)