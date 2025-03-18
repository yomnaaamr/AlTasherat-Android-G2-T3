package com.mahmoud.altasherat.features.login.domain.models

data class Phone(
    val country_code: String,
    val extension: Any,
    val holder_name: String,
    val id: Int,
    val number: String,
    val type: String
)