package com.mahmoud.altasherat.features.menu.presentation

import com.mahmoud.altasherat.features.menu.data.models.NavigationItem

class MenuContract {

    data class MenuUiState(
        val isAuthenticated: Boolean = false,
        val destinations: List<NavigationItem> = emptyList()
    )
}