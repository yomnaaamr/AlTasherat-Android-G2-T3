package com.mahmoud.altasherat.features.authentication.signup.domain.models

data class Phone(
    val id: Int,
    val countryCode: String,
    val number: String,
    val extension: String,
    val type: String,
    val holderName: String
)