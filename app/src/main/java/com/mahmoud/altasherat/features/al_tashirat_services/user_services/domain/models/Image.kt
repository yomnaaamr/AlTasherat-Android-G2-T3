package com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.models

data class Image(
    val id: Int,
    val type: String,
    val path: String,
    val title: String,
    val description: String,
    val priority: Int,
    val main: Boolean,
    val createdAt: String,
    val updatedAt: String
)