package com.mahmoud.altasherat.features.login.domain.models

data class Login(
    val message: String,
    val token: String,
    val user: User
)