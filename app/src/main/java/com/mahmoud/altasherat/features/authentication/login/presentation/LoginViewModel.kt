package com.mahmoud.altasherat.features.authentication.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.onError
import com.mahmoud.altasherat.common.domain.util.onSuccess
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase.GetCountriesFromLocalUC
import com.mahmoud.altasherat.features.authentication.login.data.models.request.LoginRequest
import com.mahmoud.altasherat.features.authentication.login.domain.useCases.LoginUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUC: LoginUC,
    private val getCountriesFromLocalUC: GetCountriesFromLocalUC
) : ViewModel() {

    private val _state = MutableStateFlow<LoginContract.LoginState>(LoginContract.LoginState.Idle)
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<LoginContract.LoginEvent>()
    val event = _event.asSharedFlow()

    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries = _countries.asStateFlow()




    init {
        viewModelScope.launch {
            getCountriesFromLocalUC()
                .onSuccess { countries->
                    _countries.value = countries
                }
                .onError {
                    _event.emit(LoginContract.LoginEvent.Error(it))
                }
        }
    }

    fun onActionTrigger(action: LoginContract.LoginAction) {
        when (action) {
            is LoginContract.LoginAction.LoginWithPhone -> loginWithPhone(action.loginRequest)
        }
    }

    private fun loginWithPhone(loginRequest: LoginRequest) {
        loginUC(loginRequest).onEach { resource ->
            when (resource) {
                is Resource.Loading -> _state.value = LoginContract.LoginState.Loading
                is Resource.Error -> {
                    _state.value =
                        LoginContract.LoginState.Exception(resource.error)
                    _event.emit(LoginContract.LoginEvent.Error(resource.error))
                }

                is Resource.Success -> {
                    _event.emit(LoginContract.LoginEvent.NavigateToHome(resource.data.user))
                    _state.value = LoginContract.LoginState.Success
                }
            }
        }.launchIn(viewModelScope)
    }
}
