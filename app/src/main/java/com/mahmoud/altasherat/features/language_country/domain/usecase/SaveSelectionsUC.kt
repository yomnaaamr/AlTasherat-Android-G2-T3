package com.mahmoud.altasherat.features.language_country.domain.usecase

import com.mahmoud.altasherat.common.domain.models.ListItem
import com.mahmoud.altasherat.features.language_country.domain.repository.ILanguageCountryRepository

class SaveSelectionsUC(
    private val repository: ILanguageCountryRepository
) {

    suspend operator fun invoke(selectedLanguage: ListItem, selectedCountry: ListItem) {
        repository.saveSelections(selectedLanguage, selectedCountry)
    }

}