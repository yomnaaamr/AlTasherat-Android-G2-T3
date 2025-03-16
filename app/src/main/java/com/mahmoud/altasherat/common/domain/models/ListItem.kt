package com.mahmoud.altasherat.common.domain.models

sealed interface ListItem {
    val id: Int
    val name: String
    val code: String
    val flag: String
    var isSelected: Boolean

}