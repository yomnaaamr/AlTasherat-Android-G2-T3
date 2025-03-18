package com.mahmoud.altasherat.common.domain.models

data class Country(
    override val id: Int,
    override val name: String,
    val currency: String,
    override val code: String,
    val phoneCode: String ,
    override val flag: String,
    override var isSelected: Boolean = false
): ListItem