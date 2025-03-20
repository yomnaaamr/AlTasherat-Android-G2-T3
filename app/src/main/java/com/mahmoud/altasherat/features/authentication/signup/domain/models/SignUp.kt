package com.mahmoud.altasherat.features.authentication.signup.domain.models

import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.models.User

data class SignUp(
    val message: String,
    val token: String,
    val user: User
)