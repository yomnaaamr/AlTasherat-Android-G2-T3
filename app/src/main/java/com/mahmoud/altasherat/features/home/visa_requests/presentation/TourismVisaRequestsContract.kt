package com.mahmoud.altasherat.features.home.visa_requests.presentation

import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.features.home.visa_requests.domain.models.TourismVisaRequest

class TourismVisaRequestsContract {

    data class TourismVisaRequestsState(
        val response: List<TourismVisaRequest> = emptyList(),
        val screenState: TourismVisaRequestsScreenState = TourismVisaRequestsScreenState.Idle
    )

    sealed interface TourismVisaRequestsAction {
        data object GetTourismVisaRequests : TourismVisaRequestsAction
    }

    sealed interface TourismVisaRequestsEvent {
        data class Error(val error: AltasheratError) : TourismVisaRequestsEvent
    }

    sealed class TourismVisaRequestsScreenState {
        data object Idle : TourismVisaRequestsScreenState()
        data object Loading : TourismVisaRequestsScreenState()
        data object Success : TourismVisaRequestsScreenState()
        data class Error(val error: AltasheratError) : TourismVisaRequestsScreenState()
    }


}