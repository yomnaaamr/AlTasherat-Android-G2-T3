package com.mahmoud.altasherat.features.authentication.login.data.models.entity

data class LoginEntity(
    val message: String,
    val token: String,
    val user: UserEntity
)