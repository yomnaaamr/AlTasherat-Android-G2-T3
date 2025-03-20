package com.mahmoud.altasherat.features.authentication.login.data.mappers

import com.mahmoud.altasherat.features.login.data.models.dto.PhoneDto
import com.mahmoud.altasherat.features.login.domain.models.Phone

object PhoneMapper:
    com.mahmoud.altasherat.features.authentication.login.data.mappers.Mapper<PhoneDto, Phone, com.mahmoud.altasherat.features.authentication.login.data.models.entity.PhoneEntity> {
    override fun dtoToEntity(input: PhoneDto): com.mahmoud.altasherat.features.authentication.login.data.models.entity.PhoneEntity {
        return com.mahmoud.altasherat.features.authentication.login.data.models.entity.PhoneEntity(
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

    override fun domainToEntity(input: Phone): com.mahmoud.altasherat.features.authentication.login.data.models.entity.PhoneEntity {
        return com.mahmoud.altasherat.features.authentication.login.data.models.entity.PhoneEntity(
            country_code = input.countryCode,
            extension = input.extension,
            holder_name = input.holderName,
            id = input.id,
            number = input.number,
            type = input.type
        )
    }
}