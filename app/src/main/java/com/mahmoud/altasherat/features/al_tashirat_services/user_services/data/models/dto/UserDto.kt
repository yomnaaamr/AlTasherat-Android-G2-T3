package com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("username") val userName: String? = null,
    @SerializedName("firstname") val firstName: String? = null,
    @SerializedName("middlename") val middleName: String? = null,
    @SerializedName("lastname") val lastName: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("phone") val phone: PhoneDto? = null,
    @SerializedName("image") val image: ImageDto? = null,
    @SerializedName("birth_date") val birthDate: String? = null,
    @SerializedName("email_verified") val emailVerified: Boolean? = null,
    @SerializedName("phone_verified") val phoneVerified: Boolean? = null,
    @SerializedName("is_blocked") val isBlocked: Int? = null
)