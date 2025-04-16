package com.mahmoud.altasherat.features.tourism_visa.data.mappers

import com.mahmoud.altasherat.features.al_tashirat_services.country.data.mappers.CountryMapper
import com.mahmoud.altasherat.features.al_tashirat_services.country.data.models.dto.CountryDto
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.mappers.ImageMapper
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.mappers.PhoneMapper
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.mappers.UserMapper
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.dto.PhoneDto
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.dto.UserDto
import com.mahmoud.altasherat.features.authentication.signup.data.mappers.RequestStateMapper
import com.mahmoud.altasherat.features.tourism_visa.data.models.dto.TourismVisaDto
import com.mahmoud.altasherat.features.tourism_visa.domain.models.TourismVisa

internal object TourismVisaMapper {
    fun dtoToDomain(model: TourismVisaDto): TourismVisa {
        return TourismVisa(
            id = model.id ?: -1,
            user = UserMapper.dtoToDomain(model.user ?: UserDto()),
            firstName = model.firstName.orEmpty(),
            middleName = model.middleName.orEmpty(),
            lastName = model.lastName.orEmpty(),
            gender = model.gender ?: 0,
            birthdate = model.birthdate.orEmpty(),
            nationality = CountryMapper.dtoToDomain(model.nationality ?: CountryDto()),
            passportNumber = model.passportNumber.orEmpty(),
            passportImages = model.passportImages?.map { it -> ImageMapper.dtoToDomain(it) }
                ?: emptyList(),
            attachments = model.attachments?.map { it -> ImageMapper.dtoToDomain(it) }
                ?: emptyList(),
            phone = PhoneMapper.dtoToDomain(model.phone ?: PhoneDto()),
            contactEmail = model.contactEmail.orEmpty(),
            destinationCountry = CountryMapper.dtoToDomain(
                model.destinationCountry ?: CountryDto()
            ),
            purposeOfVisit = model.purposeOfVisit.orEmpty(),
            adultsCount = model.adultsCount ?: 0,
            childrenCount = model.childrenCount ?: 0,
            message = model.message.orEmpty(),
            statuses = model.statuses?.map { it -> RequestStateMapper.dtoToDomain(it) }
                ?: emptyList(),
        )
    }


}