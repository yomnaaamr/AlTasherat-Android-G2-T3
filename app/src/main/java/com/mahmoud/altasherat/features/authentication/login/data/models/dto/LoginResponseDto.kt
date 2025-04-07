package com.mahmoud.altasherat.features.authentication.login.data.models.dto

import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.dto.UserDto

data class LoginResponseDto(
    val message: String,
    val token: String,
    val user: UserDto
)