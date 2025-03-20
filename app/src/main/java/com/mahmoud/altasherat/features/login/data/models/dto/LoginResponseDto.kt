package com.mahmoud.altasherat.features.login.data.models.dto

data class LoginResponseDto(
    val message: String,
    val token: String,
    val user: UserDto
)