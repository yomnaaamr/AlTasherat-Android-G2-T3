package com.mahmoud.altasherat.features.authentication.login.data.models.entity

data class UserEntity(
    val all_permissions: List<String>,
    val birthdate: String,
    val blocked: Int,
    val country: CountryEntity,
    val email: String,
    val email_verified: Boolean,
    val firstname: String,
    val id: Int,
    val image: Any? = null,
    val lastname: String,
    val middlename: String,
    val phone: com.mahmoud.altasherat.features.authentication.login.data.models.entity.PhoneEntity,
    val phone_verified: Boolean,
    val username: String
)