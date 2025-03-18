package com.mahmoud.altasherat.features.login.data.models.dto

data class PhoneDto(
    val country_code: String,
    val extension: Any,
    val holder_name: String,
    val id: Int,
    val number: String,
    val type: String
)