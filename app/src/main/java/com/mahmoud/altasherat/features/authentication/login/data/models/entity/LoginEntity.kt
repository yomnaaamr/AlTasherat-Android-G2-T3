package com.mahmoud.altasherat.features.authentication.login.data.models.entity

import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.entity.UserEntity

data class LoginEntity(
    val message: String,
    val token: String,
    val user: UserEntity
)