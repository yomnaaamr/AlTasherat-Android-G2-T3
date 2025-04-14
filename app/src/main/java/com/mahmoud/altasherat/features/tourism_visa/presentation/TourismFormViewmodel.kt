package com.mahmoud.altasherat.features.tourism_visa.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.util.onEachErrorSuspend
import com.mahmoud.altasherat.common.domain.util.onEachLoadingSuspend
import com.mahmoud.altasherat.common.domain.util.onEachSuccessSuspend
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.usecase.GetCountriesFromLocalUC
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.usecase.GetCountryUC
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.usecase.GetUserInfoUC
import com.mahmoud.altasherat.features.tourism_visa.data.models.request.StoreTourismVisaRequest
import com.mahmoud.altasherat.features.tourism_visa.domain.usecase.StoreTourismVisaUC
import com.mahmoud.altasherat.features.tourism_visa.presentation.TourismFormContract.TourismFormIntent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class TourismFormViewmodel @Inject constructor(
    private val getUserInfoUC: GetUserInfoUC,
    private val getCountriesFromLocalUC: GetCountriesFromLocalUC,
    private val getCountryUC: GetCountryUC,
    private val storeTourismForm: StoreTourismVisaUC
) : ViewModel() {
    private val _state = MutableStateFlow(TourismFormContract.TourismFormUIState())
    val state = _state.asStateFlow()

    private val _events = Channel<TourismFormContract.TourismFormEvent>()
    val events = _events.receiveAsFlow()

    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries = _countries.asStateFlow()

    private val _userCountry = MutableStateFlow<Country?>(null)
    val userCountry = _userCountry.asStateFlow()

    init {
        getCountries()
        getUserData()
        getUserCountry()
    }

    private fun getUserData() {
        getUserInfoUC()
            .onEachSuccessSuspend { result ->
                _state.update {
                    it.copy(
                        screenState = TourismFormContract.TourismFormState.Success(null),
                        firstName = result.firstname,
                        middleName = result.middleName ?: "",
                        lastName = result.lastname,
                        gender = true,
                        birthDate = result.birthDate ?: "",
                        passportNumber = "",
                        passportImages = emptyList(),
                        attachments = emptyList(),
                        countryCode = result.phone.countryCode,
                        phoneNumber = result.phone.number,
                        email = result.email,
                        destinationCountry = "",
                        purposeOfVisit = "",
                        adultsCount = 0,
                        childrenCount = 0,
                        message = ""
                    )
                }
            }
            .onEachErrorSuspend { error ->
                _events.send(TourismFormContract.TourismFormEvent.Error(error))
                _state.update {
                    it.copy(
                        screenState = TourismFormContract.TourismFormState.Error(
                            error
                        )
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun getCountries() {
        getCountriesFromLocalUC()
            .onEachSuccessSuspend { countries ->
                _countries.value = countries
            }
            .onEachErrorSuspend { error ->
                _events.send(TourismFormContract.TourismFormEvent.Error(error))
            }
            .launchIn(viewModelScope)
    }

    private fun getUserCountry() {

        getCountryUC()
            .onEachSuccessSuspend { result ->
                _userCountry.value = result
            }
            .onEachErrorSuspend { error ->
                _events.send(TourismFormContract.TourismFormEvent.Error(error))
            }.launchIn(viewModelScope)
    }

    fun onIntent(intent: TourismFormIntent) {
        when (intent) {
            is TourismFormIntent.UpdateFirstName -> _state.update { it.copy(firstName = intent.value) }
            is TourismFormIntent.UpdateMiddleName -> _state.update { it.copy(middleName = intent.value) }
            is TourismFormIntent.UpdateLastName -> _state.update { it.copy(lastName = intent.value) }
            is TourismFormIntent.UpdateGender -> _state.update { it.copy(gender = intent.isMale) }
            is TourismFormIntent.UpdateBirthDate -> _state.update { it.copy(birthDate = intent.value) }
            is TourismFormIntent.UpdatePassportNumber -> _state.update { it.copy(passportNumber = intent.value) }
            is TourismFormIntent.UpdatePassportImages -> _state.update { it.copy(passportImages = intent.files) }
            is TourismFormIntent.UpdateAttachments -> _state.update { it.copy(attachments = intent.files) }
            is TourismFormIntent.UpdateCountryCode -> _state.update { it.copy(countryCode = intent.value) }
            is TourismFormIntent.UpdatePhoneNumber -> _state.update { it.copy(phoneNumber = intent.value) }
            is TourismFormIntent.UpdateEmail -> _state.update { it.copy(email = intent.value) }
            is TourismFormIntent.UpdateDestinationCountry -> _state.update {
                it.copy(
                    destinationCountry = intent.value
                )
            }

            is TourismFormIntent.UpdatePurposeOfVisit -> _state.update { it.copy(purposeOfVisit = intent.value) }
            is TourismFormIntent.UpdateAdultsCount -> _state.update { it.copy(adultsCount = intent.count) }
            is TourismFormIntent.UpdateChildrenCount -> _state.update { it.copy(childrenCount = intent.count) }
            is TourismFormIntent.UpdateMessage -> _state.update { it.copy(message = intent.value) }
            is TourismFormIntent.SubmitForm -> submitForm()
        }
    }

    private fun submitForm() {
        val current = _state.value
        val request = StoreTourismVisaRequest(
            firstName = current.firstName,
            middleName = current.middleName,
            lastname = current.lastName,
            gender = current.gender == true,
            birthDate = current.birthDate,
            passportNumber = current.passportNumber,
            passportImages = current.passportImages,
            attachments = current.attachments,
            countryCode = current.countryCode,
            number = current.phoneNumber,
            email = current.email,
            destinationCountry = current.destinationCountry,
            purposeOfVisit = current.purposeOfVisit,
            adultsCount = current.adultsCount,
            childrenCount = current.childrenCount,
            message = current.message
        )
        storeTourismForm(request)
            .onEachSuccessSuspend { result ->
                _events.send(TourismFormContract.TourismFormEvent.NavigationToProfileMenu)
                _state.update {
                    it.copy(
                        screenState = TourismFormContract.TourismFormState.Success(
                            result.message
                        )
                    )
                }
            }
            .onEachErrorSuspend { error ->
                _events.send(TourismFormContract.TourismFormEvent.Error(error))
                _state.update {
                    it.copy(
                        screenState = TourismFormContract.TourismFormState.Error(
                            error
                        )
                    )
                }
            }
            .onEachLoadingSuspend {
                _state.update { it.copy(screenState = TourismFormContract.TourismFormState.Loading) }
            }.launchIn(viewModelScope)
    }
}