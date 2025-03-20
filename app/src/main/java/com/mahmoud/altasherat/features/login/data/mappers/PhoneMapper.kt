package com.mahmoud.altasherat.features.login.data.mappers

import com.mahmoud.altasherat.features.login.data.models.dto.PhoneDto
import com.mahmoud.altasherat.features.login.data.models.entity.PhoneEntity
import com.mahmoud.altasherat.features.login.domain.models.Phone

object PhoneMapper:Mapper<PhoneDto , Phone , PhoneEntity> {
    override fun dtoToEntity(input: PhoneDto): PhoneEntity {
        return PhoneEntity(
            country_code = input.countryCode,
            extension = input.extension,
            holder_name = input.holderName,
            id = input.id,
            number = input.number,
            type = input.type
        )
    }

    override fun dtoToDomain(input: PhoneDto): Phone {
        return Phone(
            countryCode = input.countryCode,
            extension = input.extension,
            holderName = input.holderName,
            id = input.id,
            number = input.number,
            type = input.type
        )
    }

    override fun domainToEntity(input: Phone): PhoneEntity {
        return PhoneEntity(
            country_code = input.countryCode,
            extension = input.extension,
            holder_name = input.holderName,
            id = input.id,
            number = input.number,
            type = input.type
        )
    }
}