package com.mahmoud.altasherat.features.authentication.login.domain.models


data class User(
    val all_permissions: List<String>,
    val birthdate: String,
    val blocked: Int,
    val country: Country,
    val email: String,
    val email_verified: Boolean,
    val firstname: String,
    val id: Int,
    val image: Any? = null,
    val lastname: String,
    val middlename: String,
    val phone: Phone,
    val phone_verified: Boolean,
    val username: String,
)