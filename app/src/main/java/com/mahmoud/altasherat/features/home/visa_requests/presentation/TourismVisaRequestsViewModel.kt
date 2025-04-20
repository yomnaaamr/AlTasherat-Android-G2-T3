package com.mahmoud.altasherat.features.home.visa_requests.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.util.onEachErrorSuspend
import com.mahmoud.altasherat.common.domain.util.onEachLoadingSuspend
import com.mahmoud.altasherat.common.domain.util.onEachSuccessSuspend
import com.mahmoud.altasherat.features.home.visa_requests.domain.useCases.GetTourismVisaRequestsUC
import com.mahmoud.altasherat.features.splash.domain.usecase.HasUserLoggedInUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject


@HiltViewModel
class TourismVisaRequestsViewModel @Inject constructor(
    private val getTourismVisaRequestsUC: GetTourismVisaRequestsUC,
    private val hasUserLoggedInUC: HasUserLoggedInUC,
    ) : ViewModel() {

    private val _state = MutableStateFlow(TourismVisaRequestsContract.TourismVisaRequestsState())
    val state = _state.asStateFlow()

    private val _events = Channel<TourismVisaRequestsContract.TourismVisaRequestsEvent>()
    val events = _events.receiveAsFlow()


    fun onAction(action: TourismVisaRequestsContract.TourismVisaRequestsAction) {
        when (action) {
            is TourismVisaRequestsContract.TourismVisaRequestsAction.GetTourismVisaRequests -> {
                hasUserLoggedIn(action.languageCode)
            }
        }
    }


    private fun hasUserLoggedIn(languageCode: String) {
        hasUserLoggedInUC()
            .onEachSuccessSuspend { hasUser ->
                if (hasUser) {
                    getTourismVisaRequests(languageCode)
                }else return@onEachSuccessSuspend
            }
            .onEachErrorSuspend { error ->
                _state.value = _state.value.copy(
                    screenState = TourismVisaRequestsContract.TourismVisaRequestsScreenState.Error(
                        error
                    )
                )
                _events.send(TourismVisaRequestsContract.TourismVisaRequestsEvent.Error(error))
            }
            .launchIn(viewModelScope)
    }

    private fun getTourismVisaRequests(languageCode: String) {
        getTourismVisaRequestsUC(languageCode)
            .onEachSuccessSuspend { requestsList ->
                _state.value = _state.value.copy(
                    response = requestsList.visaRequests,
                    screenState = TourismVisaRequestsContract.TourismVisaRequestsScreenState.Success
                )
            }
            .onEachErrorSuspend {
                _state.value = _state.value.copy(
                    screenState = TourismVisaRequestsContract.TourismVisaRequestsScreenState.Error(
                        it
                    )
                )
                _events.send(TourismVisaRequestsContract.TourismVisaRequestsEvent.Error(it))
            }
            .onEachLoadingSuspend {
                _state.value =
                    _state.value.copy(screenState = TourismVisaRequestsContract.TourismVisaRequestsScreenState.Loading)
            }.launchIn(viewModelScope)
    }


}