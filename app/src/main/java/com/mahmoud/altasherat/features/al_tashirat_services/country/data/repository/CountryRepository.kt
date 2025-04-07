package com.mahmoud.altasherat.features.al_tashirat_services.country.data.repository

import com.mahmoud.altasherat.features.al_tashirat_services.country.data.mappers.CountriesMapper
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.models.Countries
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.repository.ICountryRepository
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.repository.local.ICountryLocalDS
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.repository.remote.ICountryRemoteDS

internal class CountryRepository(
    private val remoteDS: ICountryRemoteDS,
    private val localDS: ICountryLocalDS
) : ICountryRepository {

    override suspend fun getCountriesFromRemote(languageCode: String): Countries {
        val response = remoteDS.getCountries(languageCode)
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


    override suspend fun hasCountries(): Boolean {
        return localDS.hasCountries()
    }

    override suspend fun getCountry(): Country {
        return localDS.getCountry()
    }


    override suspend fun saveSelectedCountry(selectedCountry: Country) {
        localDS.saveSelectedCountry(selectedCountry)
    }
}