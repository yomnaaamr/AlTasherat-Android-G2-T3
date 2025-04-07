package com.mahmoud.altasherat.features.home.menu.data.models

data class NavigationItem(
    val id: Int,
    val title: String,
    val iconRes: Int,
    val destinationId: Int,
    val requiresAuth: Boolean = false
)