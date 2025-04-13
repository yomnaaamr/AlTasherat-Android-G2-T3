package com.mahmoud.altasherat.features.tourism_visa.data.mappers

import com.mahmoud.altasherat.features.al_tashirat_services.country.data.mappers.CountryMapper
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.mappers.ImageMapper
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.mappers.PhoneMapper
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.mappers.UserMapper
import com.mahmoud.altasherat.features.tourism_visa.data.models.dto.TourismVisaDto
import com.mahmoud.altasherat.features.tourism_visa.domain.models.TourismVisa

internal object TourismVisaMapper {
    fun dtoToDomain(model: TourismVisaDto): TourismVisa {
        return TourismVisa(
            id = model.id,
            user = UserMapper.dtoToDomain(model.user),
            firstName = model.firstName,
            middleName = model.middleName,
            lastName = model.lastName,
            gender = model.gender,
            birthdate = model.birthdate,
            nationality = CountryMapper.dtoToDomain(model.nationality),
            passportNumber = model.passportNumber,
            passportImages = model.passportImages.map { it -> ImageMapper.dtoToDomain(it) },
            attachments = model.attachments,
            phone = PhoneMapper.dtoToDomain(model.phone),
            contactEmail = model.contactEmail,
            destinationCountry = CountryMapper.dtoToDomain(model.destinationCountry),
            purposeOfVisit = model.purposeOfVisit,
            adultsCount = model.adultsCount,
            childrenCount = model.childrenCount,
            message = model.message,
            statuses = model.statuses,
            canUpdateStatus = model.canUpdateStatus
        )
    }


}