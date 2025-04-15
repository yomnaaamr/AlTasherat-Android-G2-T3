package com.mahmoud.altasherat.features.home.visa_requests.data.models.dto

import com.google.gson.annotations.SerializedName

data class TourismVisaRequestsResponseDto(
    @SerializedName("data") val data: List<TourismVisaRequestDto>? = null,
)