package com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.entity

data class PhoneEntity(
    val id: Int,
    val countryCode: String,
    val number: String,
    val extension: String,
    val type: String,
    val holderName: String
)