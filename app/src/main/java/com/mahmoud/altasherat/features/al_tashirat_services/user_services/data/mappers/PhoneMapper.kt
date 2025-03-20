package com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.mappers

import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.dto.PhoneDto
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.entity.PhoneEntity
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.models.Phone


internal object PhoneMapper {
    fun dtoToDomain(model: PhoneDto): Phone {
        return Phone(
            id = model.id ?: -1,
            number = model.number.orEmpty(),
            countryCode = model.countryCode.orEmpty(),
            extension = model.extension.orEmpty(),
            type = model.type.orEmpty(),
            holderName = model.holderName.orEmpty()
        )
    }

    fun entityToDomain(model: PhoneEntity): Phone {
        return Phone(
            id = model.id,
            number = model.number,
            countryCode = model.countryCode,
            extension = model.extension,
            type = model.type,
            holderName = model.holderName
        )
    }

    fun domainToEntity(model: Phone): PhoneEntity {
        return PhoneEntity(
            id = model.id,
            number = model.number,
            countryCode = model.countryCode,
            extension = model.extension,
            type = model.type,
            holderName = model.holderName
        )
    }
}