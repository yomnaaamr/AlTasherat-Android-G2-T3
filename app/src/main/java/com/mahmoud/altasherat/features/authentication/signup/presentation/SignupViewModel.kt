package com.mahmoud.altasherat.features.authentication.signup.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.util.onEachErrorSuspend
import com.mahmoud.altasherat.common.domain.util.onEachLoadingSuspend
import com.mahmoud.altasherat.common.domain.util.onEachSuccessSuspend
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.usecase.GetCountriesFromLocalUC
import com.mahmoud.altasherat.features.al_tashirat_services.user.data.models.request.PhoneRequest
import com.mahmoud.altasherat.features.authentication.signup.data.models.request.SignUpRequest
import com.mahmoud.altasherat.features.authentication.signup.domain.usecase.SignupUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupUC: SignupUC,
    private val getCountriesFromLocalUC: GetCountriesFromLocalUC
) : ViewModel() {

    private val _state = MutableStateFlow<SignupContract.SignUpState>(SignupContract.SignUpState.Idle)
    val state = _state.asStateFlow()

    private val _events = Channel<SignupContract.SignUpEvent>()
    val events = _events.receiveAsFlow()

    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries = _countries.asStateFlow()

    private val _signUpUiState = MutableStateFlow(SignUpUiState())
    private val signUpUiState = _signUpUiState.asStateFlow()


    fun onAction(signupAction: SignupContract.SignUpAction) {
        when (signupAction) {
            is SignupContract.SignUpAction.SignUp -> signUp()
            is SignupContract.SignUpAction.UpdateCountryCode -> updateCountryCode(signupAction.countryCode)
            is SignupContract.SignUpAction.UpdateCountryID -> updateCountryID(signupAction.countryId)
            is SignupContract.SignUpAction.UpdateEmail -> updateEmail(signupAction.value)
            is SignupContract.SignUpAction.UpdateFirstName -> updateFirstName(signupAction.value)
            is SignupContract.SignUpAction.UpdateLastName -> updateLastName(signupAction.value)
            is SignupContract.SignUpAction.UpdatePassword -> updatePassword(signupAction.value)
            is SignupContract.SignUpAction.UpdatePhoneNumber -> updatePhoneNumber(signupAction.phone)
        }
    }

    init {

            getCountriesFromLocalUC()
                .onEachSuccessSuspend { countries ->
                    _countries.value = countries
                }
                .onEachErrorSuspend {
                    _events.send(SignupContract.SignUpEvent.Error(it))
                }
                .launchIn(viewModelScope)

    }

    private fun signUp() {

        val phone = PhoneRequest(
            countryCode = signUpUiState.value.countryCode,
            number = signUpUiState.value.phoneNumber,
        )

        val signupRequest = SignUpRequest(
            firstName = signUpUiState.value.firstName,
            lastname = signUpUiState.value.lastName,
            email = signUpUiState.value.email,
            password = signUpUiState.value.password,
            passwordConfirmation = signUpUiState.value.password,
            phone = phone,
            country = signUpUiState.value.selectedCountryId
        )


        signupUC(signupRequest)
            .onEachSuccessSuspend {
                _events.send(SignupContract.SignUpEvent.NavigationToHome)
                _state.value = SignupContract.SignUpState.Success(it)
            }
            .onEachErrorSuspend {
                _events.send(SignupContract.SignUpEvent.Error(it))
                _state.value = SignupContract.SignUpState.Error(it)
            }
            .onEachLoadingSuspend {
                _state.value = SignupContract.SignUpState.Loading
            }.launchIn(viewModelScope)

    }



    private fun updateFirstName(value: String) {
        _signUpUiState.update { it.copy(firstName = value) }
    }

    private fun updateLastName(value: String) {
        _signUpUiState.update { it.copy(lastName = value) }
    }

    private fun updateEmail(value: String) {
        _signUpUiState.update { it.copy(email = value) }
    }

    private fun updatePassword(value: String) {
        _signUpUiState.update { it.copy(password = value) }
    }

    private fun updatePhoneNumber(value: String) {
        _signUpUiState.update { it.copy(phoneNumber = value) }
    }

    private fun updateCountryID(countryId: String) {
        _signUpUiState.update { it.copy(selectedCountryId = countryId) }
    }

    private fun updateCountryCode(countryCode: String) {
        _signUpUiState.update { it.copy(countryCode = countryCode) }
    }
}