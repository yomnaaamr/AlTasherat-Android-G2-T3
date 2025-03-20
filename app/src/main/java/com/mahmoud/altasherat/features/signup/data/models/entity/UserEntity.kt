package com.mahmoud.altasherat.features.signup.data.models.entity

internal data class UserEntity(
    val id: Int,
    val username: String,
    val firstname: String,
    val middlename: String,
    val lastname: String,
    val email: String,
    val phone: PhoneEntity,
    val image: String,
    val birthDate: String,
    val emailVerified: Boolean,
    val phoneVerified: Boolean,
    val isBlocked: Int
)