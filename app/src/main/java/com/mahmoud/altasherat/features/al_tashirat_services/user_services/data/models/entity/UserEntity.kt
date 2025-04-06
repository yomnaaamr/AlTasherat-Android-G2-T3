package com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.entity

data class UserEntity(
    val id: Int,
    val username: String,
    val firstname: String,
    val middlename: String,
    val lastname: String,
    val email: String,
    val phone: PhoneEntity,
    val image: ImageEntity,
    val birthDate: String,
    val emailVerified: Boolean,
    val phoneVerified: Boolean,
    val isBlocked: Int
)