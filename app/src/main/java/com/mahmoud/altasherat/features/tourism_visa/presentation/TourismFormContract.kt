package com.mahmoud.altasherat.features.tourism_visa.presentation

import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.models.Country
import java.io.File

class TourismFormContract {
    data class TourismFormUIState(
        val screenState: TourismFormState = TourismFormState.Idle,

        val firstName: String = "",
        val middleName: String = "",
        val lastName: String = "",
        val gender: Int = 0,
        val birthDate: String = "",
        val passportNumber: String = "",
        val passportImages: List<File> = emptyList(),
        val attachments: List<File> = emptyList(),
        val countryCode: String = "",
        val phoneNumber: String = "",
        val email: String = "",
        val userCountry: Country? = null,
        val destinationCountry: Int = 1,
        val purposeOfVisit: String = "",
        val adultsCount: Int = 1,
        val childrenCount: Int = 1,
        val message: String? = null
    )

    sealed interface TourismFormIntent {
        data class UpdateFirstName(val value: String) : TourismFormIntent
        data class UpdateMiddleName(val value: String) : TourismFormIntent
        data class UpdateLastName(val value: String) : TourismFormIntent
        data class UpdateGender(val value: Int) : TourismFormIntent
        data class UpdateBirthDate(val value: String) : TourismFormIntent
        data class UpdatePassportNumber(val value: String) : TourismFormIntent
        data class UpdatePassportImages(val files: List<File>) : TourismFormIntent
        data class UpdateAttachments(val files: List<File>) : TourismFormIntent
        data class UpdateCountryCode(val value: String) : TourismFormIntent
        data class UpdatePhoneNumber(val value: String) : TourismFormIntent
        data class UpdateEmail(val value: String) : TourismFormIntent
        data class UpdateDestinationCountry(val value: Int) : TourismFormIntent
        data class UpdateUserCountry(val value: Country) : TourismFormIntent
        data class UpdatePurposeOfVisit(val value: String) : TourismFormIntent
        data class UpdateAdultsCount(val count: Int) : TourismFormIntent
        data class UpdateChildrenCount(val count: Int) : TourismFormIntent
        data class UpdateMessage(val value: String?) : TourismFormIntent
        object SubmitForm : TourismFormIntent
    }

    sealed interface TourismFormEvent {
        data class Error(val error: AltasheratError) : TourismFormEvent
        data object NavigationToProfileMenu : TourismFormEvent
    }

    sealed interface TourismFormState {
        data object Idle : TourismFormState
        data object Loading : TourismFormState
        data class Success(val result: String?) : TourismFormState
        data class Error(val error: AltasheratError) : TourismFormState
    }
}