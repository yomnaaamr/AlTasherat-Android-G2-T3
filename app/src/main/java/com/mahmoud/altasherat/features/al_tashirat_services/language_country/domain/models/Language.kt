package com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models

class Language(
    override val id: Int,
    override val name: String,
    override val code: String,
    override val flag: String,
    override var isSelected: Boolean = false

) : ListItem