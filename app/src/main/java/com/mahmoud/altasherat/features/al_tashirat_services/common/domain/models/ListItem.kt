package com.mahmoud.altasherat.features.al_tashirat_services.common.domain.models

interface ListItem {
    val id: Int
    val name: String
    val code: String
    val phoneCode: String
    val flag: String
    var isSelected: Boolean
}