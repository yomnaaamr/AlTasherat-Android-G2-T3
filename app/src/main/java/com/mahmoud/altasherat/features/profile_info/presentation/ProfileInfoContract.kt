package com.mahmoud.altasherat.features.profile_info.presentation

import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.models.User

class ProfileInfoContract {
    sealed interface ProfileInfoAction {
        data object SaveChanges : ProfileInfoAction
        data class UpdateFirstName(val value: String) : ProfileInfoAction

        data class UpdateMiddleName(val value: String) : ProfileInfoAction

        data class UpdateLastName(val value: String) : ProfileInfoAction

        data class UpdatePhoneNumber(val phone: String) : ProfileInfoAction
        data class UpdateCountryID(val countryId: String) : ProfileInfoAction
        data class UpdateCountryCode(val countryCode: String) : ProfileInfoAction

        data class UpdateEmail(val value: String) : ProfileInfoAction

        data class UpdateImage(val value: String) : ProfileInfoAction
        data class UpdateBirthday(val value: String) : ProfileInfoAction

    }

    sealed interface ProfileInfoEvent {
        data class Error(val error: AltasheratError) : ProfileInfoEvent
        data object NavigationToProfileMenu : ProfileInfoEvent
    }

    sealed class ProfileInfoState {
        data object Idle : ProfileInfoState()
        data object Loading : ProfileInfoState()
        data class Success(val userResponse: User) : ProfileInfoState()
        data class Error(val error: AltasheratError) : ProfileInfoState()
    }
}