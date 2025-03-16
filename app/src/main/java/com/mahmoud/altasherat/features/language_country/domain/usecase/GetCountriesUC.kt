package com.mahmoud.altasherat.features.language_country.domain.usecase

import com.mahmoud.altasherat.features.language_country.domain.repository.ILanguageCountryRepository

class GetCountriesUC(private val repository: ILanguageCountryRepository)  {

    suspend operator fun invoke() = repository.getCountries()

}