package com.mahmoud.altasherat.features.authentication.login.domain.models

import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.models.User

data class Login(
    val message: String,
    val token: String,
    val user: User
)