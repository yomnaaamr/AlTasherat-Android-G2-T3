package com.mahmoud.altasherat.features.home.visa_requests.data.mappers

import com.mahmoud.altasherat.features.al_tashirat_services.country.data.mappers.CountryMapper
import com.mahmoud.altasherat.features.al_tashirat_services.country.data.models.dto.CountryDto
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.mappers.ImageMapper
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.mappers.PhoneMapper
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.mappers.UserMapper
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.dto.PhoneDto
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.dto.UserDto
import com.mahmoud.altasherat.features.authentication.signup.data.mappers.AttachmentMapper
import com.mahmoud.altasherat.features.authentication.signup.data.mappers.RequestStateMapper
import com.mahmoud.altasherat.features.home.visa_requests.data.models.dto.TourismVisaRequestDto
import com.mahmoud.altasherat.features.home.visa_requests.domain.models.TourismVisaRequest

object TourismVisaRequestMapper {

    fun dtoToDomain(input: TourismVisaRequestDto): TourismVisaRequest {
        return TourismVisaRequest(
            id = input.id ?: 0,
            user = UserMapper.dtoToDomain(input.user ?: UserDto()),
            firstName = input.firstName.orEmpty(),
            middleName = input.middleName.orEmpty(),
            lastName = input.lastName.orEmpty(),
            gender = input.gender ?: 0,
            birthDate = input.birthDate.orEmpty(),
            nationality = CountryMapper.dtoToDomain(input.nationality ?: CountryDto()),
            passportNumber = input.passportNumber.orEmpty(),
            passportImages = input.passportImages?.map { ImageMapper.dtoToDomain(it) }
                ?: emptyList(),
            attachments = input.attachments?.map { AttachmentMapper.dtoToDomain(it) } ?: emptyList(),
            phone = PhoneMapper.dtoToDomain(input.phone ?: PhoneDto()),
            contactEmail = input.contactEmail.orEmpty(),
            destinationCountry = CountryMapper.dtoToDomain(input.destinationCountry ?: CountryDto()),
            purposeOfVisit = input.purposeOfVisit.orEmpty(),
            adultsCount = input.adultsCount ?: 0,
            childrenCount = input.childrenCount ?: 0,
            message = input.message.orEmpty(),
            statuses = input.statuses?.map { RequestStateMapper.dtoToDomain(it) } ?: emptyList()
        )
    }
}