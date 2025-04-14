package com.mahmoud.altasherat.features.home.visa_requests.data.models.dto

import com.google.gson.annotations.SerializedName

data class AttachmentDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("path") val path: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("priority") val priority: Int? = null,
    @SerializedName("main") val main: Boolean? = null,
    @SerializedName("created_at") val createdAt: String? = null,
    @SerializedName("updated_at") val updatedAt: String? = null
)