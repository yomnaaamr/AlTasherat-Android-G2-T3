package com.mahmoud.altasherat.features.home.visa_requests.data.mappers

import com.mahmoud.altasherat.features.home.visa_requests.data.models.dto.TourismVisaRequestDto
import com.mahmoud.altasherat.features.home.visa_requests.domain.models.TourismVisaRequest

object TourismVisaRequestMapper {

    fun dtoToDomain(input: TourismVisaRequestDto): TourismVisaRequest {
        return TourismVisaRequest(
            id = input.id ?: 0,
            userId = input.userId ?: 0,
            firstName = input.firstName.orEmpty(),
            middleName = input.middleName.orEmpty(),
            lastName = input.lastName.orEmpty(),
            gender = input.gender.orEmpty(),
            birthDate = input.birthDate.orEmpty(),
            nationality = input.nationality.orEmpty(),
            passportNumber = input.passportNumber.orEmpty(),
            phoneId = input.phoneId ?: 0,
            contactEmail = input.contactEmail.orEmpty(),
            countryId = input.countryId ?: 0,
            purposeOfVisit = input.purposeOfVisit.orEmpty(),
            adultsCount = input.adultsCount ?: 0,
            childrenCount = input.childrenCount ?: 0,
            message = input.message.orEmpty(),
            statusId = input.statusId ?: 0
        )

    }
}