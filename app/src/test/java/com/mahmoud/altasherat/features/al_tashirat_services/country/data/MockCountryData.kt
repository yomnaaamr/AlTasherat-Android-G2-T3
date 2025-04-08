package com.mahmoud.altasherat.features.al_tashirat_services.country.data

import com.mahmoud.altasherat.features.al_tashirat_services.country.data.models.dto.CountriesDto
import com.mahmoud.altasherat.features.al_tashirat_services.country.data.models.dto.CountryDto
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.models.Countries
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.models.Country

internal object MockCountryData {

    private val mockCountryDtoList = listOf(
        CountryDto(
            id = 1,
            name = "Egypt",
            currency = "EGP",
            code = "EG",
            phoneCode = "+20",
            flag = "https://flagcdn.com/eg.svg"
        ),
        CountryDto(
            id = 2,
            name = "United States",
            currency = "USD",
            code = "US",
            phoneCode = "+1",
            flag = "https://flagcdn.com/us.svg"
        )
    )

    val validCountriesDto = CountriesDto(
        data = mockCountryDtoList
    )

    private val mockCountryList = listOf(
        Country(
            id = 1,
            name = "Egypt",
            currency = "EGP",
            code = "EG",
            phoneCode = "+20",
            flag = "https://flagcdn.com/eg.svg",
            isSelected = false
        ),
        Country(
            id = 2,
            name = "United States",
            currency = "USD",
            code = "US",
            phoneCode = "+1",
            flag = "https://flagcdn.com/us.svg",
            isSelected = false
        )
    )

    val validCountries = Countries(
        data = mockCountryList
    )
}