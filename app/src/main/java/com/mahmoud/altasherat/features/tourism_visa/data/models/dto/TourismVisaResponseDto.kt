package com.mahmoud.altasherat.features.tourism_visa.data.models.dto

import com.google.gson.annotations.SerializedName

data class TourismVisaResponseDto(
    @SerializedName("message")
    val message: String,
    @SerializedName("tourism_visa")
    val tourismVisa: TourismVisaDto
)
