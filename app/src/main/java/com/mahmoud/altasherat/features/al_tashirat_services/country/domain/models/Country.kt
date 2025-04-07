package com.mahmoud.altasherat.features.al_tashirat_services.country.domain.models

import com.mahmoud.altasherat.features.al_tashirat_services.common.domain.models.ListItem

data class Country(
    override val id: Int,
    override val name: String,
    val currency: String,
    override val code: String,
    val phoneCode: String,
    override val flag: String,
    override var isSelected: Boolean = false
) : ListItem
