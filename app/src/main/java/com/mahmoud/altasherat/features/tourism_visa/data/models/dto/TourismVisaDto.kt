package com.mahmoud.altasherat.features.tourism_visa.data.models.dto

import com.google.gson.annotations.SerializedName
import com.mahmoud.altasherat.features.al_tashirat_services.country.data.models.dto.CountryDto
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.dto.ImageDto
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.dto.PhoneDto
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.dto.UserDto
import com.mahmoud.altasherat.features.home.visa_requests.data.models.dto.StatusDto

class TourismVisaDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("user")
    val user: UserDto,
    @SerializedName("firstname")
    val firstName: String,
    @SerializedName("middlename")
    val middleName: String,
    @SerializedName("lastname")
    val lastName: String,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("birthdate")
    val birthdate: String,
    @SerializedName("nationality")
    val nationality: CountryDto,
    @SerializedName("passport_number")
    val passportNumber: String,
    @SerializedName("passport_images")
    val passportImages: List<ImageDto>,
    @SerializedName("attachments")
    val attachments: List<ImageDto>,
    @SerializedName("phone")
    val phone: PhoneDto,
    @SerializedName("contact_email")
    val contactEmail: String,
    @SerializedName("destination_country")
    val destinationCountry: CountryDto,
    @SerializedName("purpose_of_visit")
    val purposeOfVisit: String,
    @SerializedName("adults_count")
    val adultsCount: Int,
    @SerializedName("children_count")
    val childrenCount: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("statuses")
    val statuses: List<StatusDto>
)
