package com.mahmoud.altasherat.features.login.data.models.entity

data class PhoneEntity(
    val country_code: String? = null,
    val extension: Any? = null,
    val holder_name: String? = null,
    val id: Int,
    val number: String,
    val type: String
)