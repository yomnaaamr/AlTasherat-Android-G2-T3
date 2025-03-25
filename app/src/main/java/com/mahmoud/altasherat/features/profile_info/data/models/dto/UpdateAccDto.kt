package com.mahmoud.altasherat.features.profile_info.data.models.dto

import com.google.gson.annotations.SerializedName
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.dto.UserDto

data class UpdateAccDto(
    @SerializedName("message") val message: String? = null,
    @SerializedName("user") val user: UserDto? = null
)