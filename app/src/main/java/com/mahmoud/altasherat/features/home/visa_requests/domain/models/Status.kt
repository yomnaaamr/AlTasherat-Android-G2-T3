package com.mahmoud.altasherat.features.home.visa_requests.domain.models

data class Status(
    val id: Int,
    val name: String,
    val color: String,
    val activatedAt: String,
    val main: Boolean,
    val active: Boolean
)