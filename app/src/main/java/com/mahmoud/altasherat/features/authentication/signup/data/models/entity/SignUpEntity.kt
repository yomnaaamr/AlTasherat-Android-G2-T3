package com.mahmoud.altasherat.features.authentication.signup.data.models.entity

internal data class SignUpEntity(
    val token: String,
    val message: String,
    val user: UserEntity
)