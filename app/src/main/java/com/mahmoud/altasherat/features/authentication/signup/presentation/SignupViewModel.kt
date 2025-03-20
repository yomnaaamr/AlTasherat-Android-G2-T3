package com.mahmoud.altasherat.features.authentication.signup.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.request.PhoneRequest
import com.mahmoud.altasherat.features.authentication.signup.data.models.request.SignUpRequest
import com.mahmoud.altasherat.features.authentication.signup.domain.usecase.SignupUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupUC: SignupUC
) : ViewModel() {

    private val _state = MutableStateFlow<SignUpState>(SignUpState.Idle)
    val state = _state.asStateFlow()

    private val _events = Channel<SignUpEvent>()
    val events = _events.receiveAsFlow()

    private val _signUpUiState = MutableStateFlow(SignUpUiState())
    private val signUpUiState = _signUpUiState.asStateFlow()


    fun onAction(signupAction: SignUpAction) {
        when (signupAction) {
            is SignUpAction.SignUp -> signUp()
            is SignUpAction.UpdateCountryCode -> updateCountryCode(signupAction.countryCode)
            is SignUpAction.UpdateCountryID -> updateCountryID(signupAction.countryId)
            is SignUpAction.UpdateEmail -> updateEmail(signupAction.value)
            is SignUpAction.UpdateFirstName -> updateFirstName(signupAction.value)
            is SignUpAction.UpdateLastName -> updateLastName(signupAction.value)
            is SignUpAction.UpdatePassword -> updatePassword(signupAction.value)
            is SignUpAction.UpdatePhoneNumber -> updatePhoneNumber(signupAction.phone)
        }
    }

    private fun signUp() {

        val phone = PhoneRequest(
//            countryCode = signUpUiState.value.countryCode,
            number = signUpUiState.value.phoneNumber,
            countryCode = "0020",
        )

        val signupRequest = SignUpRequest(
            firstName = signUpUiState.value.firstName,
            lastname = signUpUiState.value.lastName,
            email = signUpUiState.value.email,
            password = signUpUiState.value.password,
            passwordConfirmation = signUpUiState.value.password,
            phone = phone,
            country = "1"
        )


        signupUC(signupRequest)
            .onEach { result ->
                _state.value = when (result) {
                    is Resource.Error -> {
                        _events.send(SignUpEvent.Error(result.error))
                        SignUpState.Error(result.error)
                    }

                    is Resource.Loading -> SignUpState.Loading
                    is Resource.Success -> {
                        _events.send(SignUpEvent.NavigationToHome)
                        SignUpState.Success(result.data)
                    }
                }
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