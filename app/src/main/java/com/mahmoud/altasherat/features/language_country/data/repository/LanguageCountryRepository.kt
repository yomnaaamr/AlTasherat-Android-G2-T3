package com.mahmoud.altasherat.features.language_country.data.repository

import com.mahmoud.altasherat.common.domain.models.Country
import com.mahmoud.altasherat.features.language_country.domain.repository.ILanguageCountryRepository
import com.mahmoud.altasherat.features.language_country.domain.repository.local.ILanguageCountryLocalDS
import com.mahmoud.altasherat.features.splash.data.mappers.SplashResponseMapper

internal class LanguageCountryRepository(
    private val localDS: ILanguageCountryLocalDS
): ILanguageCountryRepository {

    override suspend fun getCountries(): List<Country> {
        val result = localDS.getCountries()
        return SplashResponseMapper.entityToDomain(result).data
    }
}