package com.mahmoud.altasherat.features.home.visa_requests.data.models.dto

import com.google.gson.annotations.SerializedName

data class TourismVisaRequestDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("user_id") val userId: Int? = null,
    @SerializedName("firstname") val firstName: String? = null,
    @SerializedName("middlename") val middleName: String? = null,
    @SerializedName("lastname") val lastName: String? = null,
    @SerializedName("gender") val gender: String? = null,
    @SerializedName("birthdate") val birthDate: String? = null,
    @SerializedName("nationality") val nationality: String? = null,
    @SerializedName("passport_number") val passportNumber: String? = null,
    @SerializedName("phone_id") val phoneId: Int? = null,
    @SerializedName("contact_email") val contactEmail: String? = null,
    @SerializedName("country_id") val countryId: Int? = null,
    @SerializedName("purpose_of_visit") val purposeOfVisit: String? = null,
    @SerializedName("adults_count") val adultsCount: Int? = null,
    @SerializedName("children_count") val childrenCount: Int? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("status_id") val statusId: Int? = null
)