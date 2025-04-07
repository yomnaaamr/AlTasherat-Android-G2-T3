package com.mahmoud.altasherat.features.menu.presentation.fragments

import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.models.User

class ContactUsContract {


    data class ContactUsState(
        val user: User? = null,
        val screenState: ContactUsScreenState = ContactUsScreenState.Idle
    )

    sealed interface ContactUsEvent {
        data class Error(val error: AltasheratError) : ContactUsEvent
    }

    sealed interface ContactUsScreenState {
        data object Idle : ContactUsScreenState
        data object Loading : ContactUsScreenState
        data object Success : ContactUsScreenState
        data class Error(val error: AltasheratError) : ContactUsScreenState
    }


    sealed interface ContactUsAction {

    }

}