package com.mahmoud.altasherat.features.authentication.signup.domain.models

data class SignUp(
    val message: String,
    val token: String,
    val user: User
)