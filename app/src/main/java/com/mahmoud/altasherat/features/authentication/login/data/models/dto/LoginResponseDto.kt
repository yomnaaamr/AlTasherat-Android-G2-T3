package com.mahmoud.altasherat.features.authentication.login.data.models.dto

data class LoginResponseDto(
    val message: String,
    val token: String,
    val user: UserDto
)