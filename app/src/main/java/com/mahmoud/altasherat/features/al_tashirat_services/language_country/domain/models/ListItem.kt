package com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models

sealed interface ListItem {
    val id: Int
    val name: String
    val code: String
    val flag: String
    var isSelected: Boolean
}