package com.mahmoud.altasherat.features.authentication.signup.presentation

import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Country
import com.mahmoud.altasherat.features.authentication.signup.domain.models.SignUp

class SignupContract {

    sealed interface SignUpAction {

        data object SignUp :
            SignUpAction

        data class UpdateFirstName(val value: String) :
            SignUpAction

        data class UpdateLastName(val value: String) :
            SignUpAction

        data class UpdateEmail(val value: String) :
            SignUpAction

        data class UpdatePassword(val value: String) :
            SignUpAction

        data class UpdatePhoneNumber(val phone: String) :
            SignUpAction

        data class UpdateCountryID(val countryId: String) :
            SignUpAction

        data class UpdateCountryCode(val countryCode: String) :
            SignUpAction
    }

    sealed interface SignUpEvent {
        data class Error(val error: AltasheratError) : SignUpEvent
        data object NavigationToHome: SignUpEvent
    }

    sealed class SignUpState {
        data object Idle : SignUpState()
        data object Loading : SignUpState()
        data class Success (val signupResponse: SignUp) : SignUpState()
        data class Error(val error: AltasheratError) : SignUpState()
    }
}