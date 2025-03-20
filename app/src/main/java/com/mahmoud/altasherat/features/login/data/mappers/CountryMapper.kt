package com.mahmoud.altasherat.features.login.data.mappers

import com.mahmoud.altasherat.features.login.data.models.dto.CountryDto
import com.mahmoud.altasherat.features.login.data.models.entity.CountryEntity
import com.mahmoud.altasherat.features.login.domain.models.Country

object CountryMapper :Mapper<CountryDto , Country ,CountryEntity> {
    override fun dtoToEntity(input: CountryDto): CountryEntity {
        return CountryEntity(
            id = input.id,
            name = input.name,
            code = input.code,
            phone_code = input.phone_code,
            currency = input.currency,
            flag = input.flag,
        )
    }

    override fun dtoToDomain(input: CountryDto): Country {
        return Country(
            id = input.id,
            name = input.name,
            code = input.code,
            phone_code = input.phone_code,
            currency = input.currency,
            flag = input.flag,
        )
    }

    override fun domainToEntity(input: Country): CountryEntity {
        return CountryEntity(
            id = input.id,
            name = input.name,
            code = input.code,
            phone_code = input.phone_code,
            currency = input.currency,
            flag = input.flag,
        )
    }
}