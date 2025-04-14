package com.mahmoud.altasherat.features.home.visa_requests.domain.models

data class Attachment(
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