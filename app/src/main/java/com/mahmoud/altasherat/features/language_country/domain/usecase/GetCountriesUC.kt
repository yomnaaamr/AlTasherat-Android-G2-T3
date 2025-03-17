package com.mahmoud.altasherat.features.language_country.domain.usecase

import com.mahmoud.altasherat.common.domain.models.Country
import com.mahmoud.altasherat.features.language_country.domain.repository.ILanguageCountryRepository

class GetCountriesUC(private val repository: ILanguageCountryRepository)  {

    suspend operator fun invoke(): List<Country>{
        return repository.getCountries()
    }

}