package com.mahmoud.altasherat.features.al_tashirat_services.language_country.data.mappers

import com.mahmoud.altasherat.features.al_tashirat_services.language_country.data.models.dto.CountryDto
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.data.models.entity.CountryEntity
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Country

internal object CountryMapper {
    fun dtoToDomain(model: CountryDto): Country {
        return Country(
            id = model.id ?: -1,
            name = model.name.orEmpty(),
            code = model.code.orEmpty(),
            phoneCode = model.phoneCode.orEmpty(),
            flag = model.flag.orEmpty(),
            currency = model.currency.orEmpty()
        )
    }

    fun entityToDomain(model: CountryEntity): Country {
        return Country(
            id = model.id,
            name = model.name,
            code = model.code,
            phoneCode = model.phoneCode,
            flag = model.flag,
            currency = model.currency
        )
    }

    fun domainToEntity(model: Country): CountryEntity {
        return CountryEntity(
            id = model.id,
            name = model.name,
            code = model.code,
            phoneCode = model.phoneCode,
            flag = model.flag,
            currency = model.currency
        )
    }
}