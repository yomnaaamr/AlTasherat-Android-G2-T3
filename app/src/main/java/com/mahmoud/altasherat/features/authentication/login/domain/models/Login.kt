package com.mahmoud.altasherat.features.authentication.login.domain.models

data class Login(
    val message: String,
    val token: String,
    val user: User
)