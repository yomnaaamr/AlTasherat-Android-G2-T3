package com.mahmoud.altasherat.features.delete_account.delete_acc.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.usecase.DeleteUserAccessToken
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.usecase.DeleteUserInfoUC
import com.mahmoud.altasherat.features.delete_account.delete_acc.data.models.request.DeleteAccRequest
import com.mahmoud.altasherat.features.delete_account.delete_acc.domain.usecase.DeleteAccountUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class DeleteAccountViewModel @Inject constructor(
    private val deleteAccUC: DeleteAccountUC,
    private val deleteTokenUC: DeleteUserAccessToken,
    private val deleteUserUC: DeleteUserInfoUC,
) : ViewModel() {
    private val _state =
        MutableStateFlow<DeleteAccountContract.DeleteAccountState>(DeleteAccountContract.DeleteAccountState.Idle)
    val state = _state.asStateFlow()

    private val _events = Channel<DeleteAccountContract.DeleteAccountEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(deleteAccAction: DeleteAccountContract.DeleteAccountAction) {
        when (deleteAccAction) {
            is DeleteAccountContract.DeleteAccountAction.DeleteAccount -> deleteAccount(
                deleteAccAction.password
            )
        }
    }

    private fun deleteAccount(password: String) {
        val deleteRequest = DeleteAccRequest(password)
        deleteAccUC.invoke(deleteRequest).onEach { result ->
            _state.value = when (result) {
                is Resource.Loading -> DeleteAccountContract.DeleteAccountState.Loading
                is Resource.Error -> {
                    _events.send(DeleteAccountContract.DeleteAccountEvent.Error(result.error))
                    DeleteAccountContract.DeleteAccountState.Error(result.error)
                }

                is Resource.Success -> {
                    deleteDataFromLocal()
                    DeleteAccountContract.DeleteAccountState.Success(result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun deleteDataFromLocal() {
        deleteTokenUC().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    deleteUserUC().onEach { result1 ->
                        when (result1) {
                            is Resource.Success -> {
                                _events.send(DeleteAccountContract.DeleteAccountEvent.NavigationToDashboard)
                            }

                            is Resource.Error -> {
                                _events.send(
                                    DeleteAccountContract.DeleteAccountEvent.Error(
                                        result1.error
                                    )
                                )
                            }

                            else -> {}
                        }
                    }.launchIn(viewModelScope)
                }

                is Resource.Error -> {
                    _events.send(
                        DeleteAccountContract.DeleteAccountEvent.Error(
                            result.error
                        )
                    )
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }
}