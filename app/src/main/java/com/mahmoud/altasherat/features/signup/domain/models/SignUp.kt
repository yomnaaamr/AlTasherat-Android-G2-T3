package com.mahmoud.altasherat.features.signup.domain.models

data class SignUp(
    val message: String,
    val token: String,
    val user: User
)