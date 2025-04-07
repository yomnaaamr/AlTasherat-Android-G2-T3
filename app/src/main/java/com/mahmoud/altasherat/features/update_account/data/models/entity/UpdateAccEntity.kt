package com.mahmoud.altasherat.features.update_account.data.models.entity

import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.entity.UserEntity

data class UpdateAccEntity(
    val message: String,
    val user: UserEntity
)