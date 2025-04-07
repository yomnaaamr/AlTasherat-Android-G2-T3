package com.mahmoud.altasherat.features.al_tashirat_services.language.domain.models

import com.mahmoud.altasherat.features.al_tashirat_services.common.domain.models.ListItem

class Language(
    override val id: Int,
    override val name: String,
    override val code: String,
    override val flag: String,
    override var isSelected: Boolean = false

) : ListItem