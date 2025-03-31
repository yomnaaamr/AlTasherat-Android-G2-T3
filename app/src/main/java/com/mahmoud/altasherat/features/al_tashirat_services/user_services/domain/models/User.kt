package com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.models

data class User(
    val id: Int,
    val username: String,
    val firstname: String,
    val middleName: String?,
    val lastname: String,
    val email: String,
    val phone: Phone,
    val image: Image?,
    val birthDate: String?,
    val emailVerified: Boolean,
    val phoneVerified: Boolean,
    val isBlocked: Int
)