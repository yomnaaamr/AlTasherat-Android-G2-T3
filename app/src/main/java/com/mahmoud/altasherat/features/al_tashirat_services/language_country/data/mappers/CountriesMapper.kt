package com.mahmoud.altasherat.features.al_tashirat_services.language_country.data.mappers

import com.mahmoud.altasherat.features.al_tashirat_services.language_country.data.models.dto.CountriesDto
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.data.models.entity.CountriesEntity
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Countries

internal object CountriesMapper {

    fun dtoToDomain(model: CountriesDto): Countries {
        return Countries(
            data = model.data?.map { CountryMapper.dtoToDomain(it) } ?: emptyList()
        )
    }

    fun domainToEntity(model: Countries): CountriesEntity {
        return CountriesEntity(
            data = model.data.map { CountryMapper.domainToEntity(it) }
        )
    }

    fun entityToDomain(model: CountriesEntity): Countries {
        return Countries(
            data = model.data.map { CountryMapper.entityToDomain(it) }
        )

    }
}