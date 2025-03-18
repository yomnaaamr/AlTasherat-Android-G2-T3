package com.mahmoud.altasherat.features.login.data.models.dto

data class UserDto(
    val all_permissions: List<String>,
    val birthdate: String,
    val blocked: Int,
    val country: CountryDto,
    val email: String,
    val email_verified: Boolean,
    val firstname: String,
    val id: Int,
    val image: Any,
    val lastname: String,
    val middlename: String,
    val phone: PhoneDto,
    val phone_verified: Boolean,
    val username: String
)