package com.mahmoud.altasherat.features.home.visa_requests.data.models.dto

import com.google.gson.annotations.SerializedName

data class TourismVisaRequestsResponseDto(
    @SerializedName("msg") val message: String? = null,
    @SerializedName("data") val visaRequests: List<TourismVisaRequestDto>? = null,
)