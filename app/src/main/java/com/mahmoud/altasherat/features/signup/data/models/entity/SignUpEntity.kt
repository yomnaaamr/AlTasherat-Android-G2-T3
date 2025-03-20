package com.mahmoud.altasherat.features.signup.data.models.entity

internal data class SignUpEntity(
    val token: String,
    val message: String,
    val user: UserEntity
)