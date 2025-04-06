package com.mahmoud.altasherat.features.delete_account.delete_acc.presentation

import com.mahmoud.altasherat.common.domain.util.error.AltasheratError

class DeleteAccountContract {
    sealed interface DeleteAccountAction {
        data class DeleteAccount(val password: String) : DeleteAccountAction
    }

    sealed interface DeleteAccountEvent {
        data class Error(val error: AltasheratError) : DeleteAccountEvent
        data object NavigationToDashboard : DeleteAccountEvent
    }

    sealed class DeleteAccountState {
        data object Idle : DeleteAccountState()
        data object Loading : DeleteAccountState()
        data class Success(val message: String) : DeleteAccountState()
        data class Error(val error: AltasheratError) : DeleteAccountState()
    }
}