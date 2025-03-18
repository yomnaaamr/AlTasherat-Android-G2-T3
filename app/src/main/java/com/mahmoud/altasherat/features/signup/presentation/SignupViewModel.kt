package com.mahmoud.altasherat.features.signup.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.features.signup.data.models.request.SignUpRequest
import com.mahmoud.altasherat.features.signup.domain.models.Phone
import com.mahmoud.altasherat.features.signup.domain.usecase.SignupUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject


@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupUC: SignupUC
) : ViewModel() {

    private val _state = MutableStateFlow<SignUpState>(SignUpState.Idle)
    val state = _state.asStateFlow()

    private val _events = Channel<SignUpEvent>()
    val events = _events.receiveAsFlow()


    fun onAction(signupAction: SignUpAction) {
        when (signupAction) {
            SignUpAction.SignUp -> signUp()
        }
    }


    init {
        signUp()
    }

    private fun signUp() {

        val phone = Phone(
            id = 0,
            countryCode = "0020",
            number = "1279411825",
            extension = "",
            type = "",
            holderName = ""
        )

        val signupRequest = SignUpRequest(
            firstName = "menna",
            lastname = "ahmed",
            email = "yomna1234@gmail.com",
            password = "12345678",
            passwordConfirmation = "12345678",
            phone = phone,
            country = "1"
        )


        signupUC(signupRequest)
            .onEach { result ->
                _state.value = when (result) {
                    is Resource.Error -> {
                        Log.d("SignupViewModel", "signUp: ${result.error}")
                        _events.send(SignUpEvent.Error(result.error))
                        SignUpState.Error(result.error)
                    }

                    is Resource.Loading -> SignUpState.Loading
                    is Resource.Success -> {
                        Log.d("SignupViewModel", "signUp: ${result.data}")
                        SignUpState.Success(result.data)
                    }
                }
            }.launchIn(viewModelScope)
    }
}