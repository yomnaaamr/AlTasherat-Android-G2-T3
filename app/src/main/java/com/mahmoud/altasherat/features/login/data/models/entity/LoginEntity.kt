package com.mahmoud.altasherat.features.login.data.models.entity

data class LoginEntity(
    val message: String,
    val token: String,
    val user: UserEntity
)