package com.mahmoud.altasherat.common.domain.models

data class Country(
    override val id: Int = 1,
    override val name: String = "مصر",
    val currency: String = "EGP",
    override val code: String= "EG",
    val phoneCode: String = "+20",
    override val flag: String = "none",
    override var isSelected: Boolean = false
): ListItem