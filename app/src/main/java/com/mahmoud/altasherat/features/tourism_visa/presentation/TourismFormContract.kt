package com.mahmoud.altasherat.features.tourism_visa.presentation

import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import java.io.File

class TourismFormContract {
    data class TourismFormUIState(
        val screenState: TourismFormState = TourismFormState.Idle,

        val firstName: String = "",
        val middleName: String = "",
        val lastName: String = "",
        val gender: Boolean? = null, // true = male, false = female
        val birthDate: String = "",
        val passportNumber: String = "",
        val passportImages: List<File> = emptyList(),
        val attachments: List<File> = emptyList(),
        val countryCode: String = "",
        val phoneNumber: String = "",
        val email: String = "",
        val destinationCountry: String = "",
        val purposeOfVisit: String = "",
        val adultsCount: Int = 1,
        val childrenCount: Int = 0,
        val message: String? = null
    )

    sealed interface TourismFormIntent {
        // Basic identity fields
        data class UpdateFirstName(val value: String) : TourismFormIntent
        data class UpdateMiddleName(val value: String) : TourismFormIntent
        data class UpdateLastName(val value: String) : TourismFormIntent

        // Gender (true = male, false = female)
        data class UpdateGender(val isMale: Boolean) : TourismFormIntent

        // Birth date
        data class UpdateBirthDate(val value: String) : TourismFormIntent

        // Passport info
        data class UpdatePassportNumber(val value: String) : TourismFormIntent
        data class UpdatePassportImages(val files: List<File>) : TourismFormIntent

        // Attachments
        data class UpdateAttachments(val files: List<File>) : TourismFormIntent

        // Phone
        data class UpdateCountryCode(val value: String) : TourismFormIntent
        data class UpdatePhoneNumber(val value: String) : TourismFormIntent

        // Contact
        data class UpdateEmail(val value: String) : TourismFormIntent

        // Travel details
        data class UpdateDestinationCountry(val value: String) : TourismFormIntent
        data class UpdatePurposeOfVisit(val value: String) : TourismFormIntent
        data class UpdateAdultsCount(val count: Int) : TourismFormIntent
        data class UpdateChildrenCount(val count: Int) : TourismFormIntent

        // Optional message
        data class UpdateMessage(val value: String?) : TourismFormIntent

        // Form submission
        object SubmitForm : TourismFormIntent
    }

    sealed interface TourismFormEvent {
        data class Error(val error: AltasheratError) : TourismFormEvent
        data object NavigationToProfileMenu : TourismFormEvent
    }

    sealed interface TourismFormState {
        data object Idle : TourismFormState
        data object Loading : TourismFormState
        data object Success : TourismFormState
        data class Error(val error: AltasheratError) : TourismFormState
    }
}