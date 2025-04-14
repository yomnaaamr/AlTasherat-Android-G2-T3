package com.mahmoud.altasherat.features.home.visa_requests.domain.models

data class TourismVisaRequest(
    val id: Int,
    val userId: Int,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val gender: String,
    val birthDate: String,
    val nationality: String,
    val passportNumber: String,
    val phoneId: Int,
    val contactEmail: String,
    val countryId: Int,
    val purposeOfVisit: String,
    val adultsCount: Int,
    val childrenCount: Int,
    val message: String,
    val statusId: Int
)