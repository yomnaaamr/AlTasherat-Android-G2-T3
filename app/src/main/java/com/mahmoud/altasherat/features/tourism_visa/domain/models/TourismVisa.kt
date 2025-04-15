package com.mahmoud.altasherat.features.tourism_visa.domain.models

import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.models.Image
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.models.Phone
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.models.User
import com.mahmoud.altasherat.features.home.visa_requests.domain.models.Status

data class TourismVisa(
    val id: Int,
    val user: User,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val gender: Int,
    val birthdate: String,
    val nationality: Country,
    val passportNumber: String,
    val passportImages: List<Image>,
    val attachments: List<Image>,
    val phone: Phone,
    val contactEmail: String,
    val destinationCountry: Country,
    val purposeOfVisit: String,
    val adultsCount: Int,
    val childrenCount: Int,
    val message: String,
    val statuses: List<Status>,
)
