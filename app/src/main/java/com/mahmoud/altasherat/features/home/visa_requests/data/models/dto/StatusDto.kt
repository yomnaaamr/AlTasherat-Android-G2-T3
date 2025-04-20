package com.mahmoud.altasherat.features.home.visa_requests.data.models.dto

import com.google.gson.annotations.SerializedName

data class StatusDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("color") val color: String? = null,
    @SerializedName("activated_at") val activatedAt: String? = null,
    @SerializedName("main") val main: Boolean? = null,
    @SerializedName("active") val active: Boolean? = null
)