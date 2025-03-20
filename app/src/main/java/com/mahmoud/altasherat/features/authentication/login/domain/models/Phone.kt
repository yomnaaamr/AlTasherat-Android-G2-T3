package com.mahmoud.altasherat.features.authentication.login.domain.models


data class Phone(
    val id: Int,
    val countryCode: String?= null,
    val extension: Any? = null,
    val holderName: String? = null,
    val number: String,
    val type: String
)