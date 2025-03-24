package com.mahmoud.altasherat.features.al_tashirat_services.language_country.data.repository

import com.mahmoud.altasherat.features.al_tashirat_services.language_country.data.mappers.CountriesMapper
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Countries
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Language
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.repository.ILanguageCountryRepository
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.repository.local.ILanguageCountryLocalDS
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.repository.remote.ILanguageCountryRemoteDS

internal class LanguageCountryRepository(
    private val remoteDS: ILanguageCountryRemoteDS,
    private val localDS: ILanguageCountryLocalDS
) : ILanguageCountryRepository {

    override suspend fun getCountriesFromRemote(): Countries {
        val response = remoteDS.getCountries()
        return CountriesMapper.dtoToDomain(response)
    }

    override suspend fun savaCountriesToLocal(countriesResponse: Countries) {
        val result = CountriesMapper.domainToEntity(countriesResponse)
        localDS.savaCountries(result)
    }

    override suspend fun getCountriesFromLocal(): List<Country> {
        val result = localDS.getCountries()
        return CountriesMapper.entityToDomain(result).data
    }

    override suspend fun saveSelections(selectedLanguage: Language, selectedCountry: Country) {
        localDS.saveSelections(selectedLanguage, selectedCountry)
    }

    override suspend fun getLanguageCode(): String? {
        return localDS.getLanguageCode()
    }

    override suspend fun hasCountries(): Boolean {
        return localDS.hasCountries()
    }

    override suspend fun getCountry(): Country {
        return localDS.getCountry()
    }
}