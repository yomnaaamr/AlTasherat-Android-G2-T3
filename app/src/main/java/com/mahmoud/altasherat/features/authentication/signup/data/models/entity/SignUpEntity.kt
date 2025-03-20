package com.mahmoud.altasherat.features.authentication.signup.data.models.entity

import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.entity.UserEntity

internal data class SignUpEntity(
    val token: String,
    val message: String,
    val user: UserEntity
)