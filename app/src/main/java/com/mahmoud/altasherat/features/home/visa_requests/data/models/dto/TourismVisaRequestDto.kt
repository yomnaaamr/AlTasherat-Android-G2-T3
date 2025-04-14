package com.mahmoud.altasherat.features.home.visa_requests.data.models.dto

import com.google.gson.annotations.SerializedName
import com.mahmoud.altasherat.features.al_tashirat_services.country.data.models.dto.CountryDto
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.dto.ImageDto
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.dto.PhoneDto
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.dto.UserDto

data class TourismVisaRequestDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("user") val user: UserDto? = null,
    @SerializedName("firstname") val firstName: String? = null,
    @SerializedName("middlename") val middleName: String? = null,
    @SerializedName("lastname") val lastName: String? = null,
    @SerializedName("gender") val gender: Int? = null,
    @SerializedName("birthdate") val birthDate: String? = null,
    @SerializedName("nationality") val nationality: CountryDto? = null,
    @SerializedName("passport_number") val passportNumber: String? = null,
    @SerializedName("passport_images") val passportImages: List<ImageDto>? = null,
    @SerializedName("attachments") val attachments: List<AttachmentDto>? = null,
    @SerializedName("phone") val phone: PhoneDto? = null,
    @SerializedName("contact_email") val contactEmail: String? = null,
    @SerializedName("destination_country") val destinationCountry: CountryDto? = null,
    @SerializedName("purpose_of_visit") val purposeOfVisit: String? = null,
    @SerializedName("adults_count") val adultsCount: Int? = null,
    @SerializedName("children_count") val childrenCount: Int? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("statuses") val statuses: List<StatusDto>? = null
)