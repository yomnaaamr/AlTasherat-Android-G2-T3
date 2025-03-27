package com.mahmoud.altasherat.features.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.onError
import com.mahmoud.altasherat.common.domain.util.onSuccess
import com.mahmoud.altasherat.common.util.Constants
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase.GetCountriesFromRemoteUC
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase.GetLanguageCodeUC
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase.HasCountriesUC
import com.mahmoud.altasherat.features.onBoarding.domain.useCase.GetOnBoardingStateUC
import com.mahmoud.altasherat.features.splash.domain.usecase.HasUserLoggedInUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getCountriesFromRemoteUC: GetCountriesFromRemoteUC,
    private val getLanguageCodeUC: GetLanguageCodeUC,
    private val hasUserLoggedInUC: HasUserLoggedInUC,
    private val hasCountriesUC: HasCountriesUC,
    private val getOnBoardingStateUC: GetOnBoardingStateUC
) : ViewModel() {

    private val _state =
        MutableStateFlow<SplashContract.SplashState>(SplashContract.SplashState.Idle)
    val state = _state.asStateFlow()

    private val _events = Channel<SplashContract.SplashEvent>()
    val events = _events.receiveAsFlow()

    private val _languageCode = MutableStateFlow<String?>(null)
    val languageCode: StateFlow<String?> = _languageCode



    init {

        viewModelScope.launch {
            hasCountriesUC()
                .onSuccess { hasCountries ->
                    if (hasCountries) {
                        isUserFirstTime()
                    } else {
                        fetchCountries()
                    }
                }
                .onError {
                    _events.send(SplashContract.SplashEvent.Error(it))
                    _state.value = SplashContract.SplashState.Error(it)
                }
        }


        getLanguageCode()

    }


    private fun isUserFirstTime() {
        viewModelScope.launch {
            getOnBoardingStateUC()
                .onSuccess { notFirstTime ->
                    if (notFirstTime) {
                        hasLoggedInUser()
                    } else {
                        _events.send(SplashContract.SplashEvent.NavigateToLanguage)
                        _state.value = SplashContract.SplashState.Success
                    }
                }
        }
    }


    private fun hasLoggedInUser() {
        viewModelScope.launch {
            hasUserLoggedInUC()
                .onSuccess { hasUser ->
                    if (hasUser) {
                        _events.send(SplashContract.SplashEvent.NavigateToHome)
                    } else {
                        _events.send(SplashContract.SplashEvent.NavigateToAuth)
                    }
                    _state.value = SplashContract.SplashState.Success
                }
                .onError {
                    _events.send(SplashContract.SplashEvent.Error(it))
                    _state.value = SplashContract.SplashState.Error(it)
                }
        }
    }


    private fun fetchCountries() {

        getCountriesFromRemoteUC(Constants.LOCALE_EN)
            .onEach { result ->
                _state.value = when (result) {
                    is Resource.Error -> {
                        _events.send(SplashContract.SplashEvent.Error(result.error))
                        SplashContract.SplashState.Error(result.error)
                    }

                    is Resource.Loading -> SplashContract.SplashState.Loading
                    is Resource.Success -> {
                        _events.send(SplashContract.SplashEvent.NavigateToLanguage)
                        SplashContract.SplashState.Success
                    }
                }
            }
            .launchIn(viewModelScope)

    }

    private fun getLanguageCode() {
        viewModelScope.launch {
            getLanguageCodeUC()
                .onSuccess { languageCode ->
                    _languageCode.value = languageCode
                }
                .onError {
                    _events.send(SplashContract.SplashEvent.Error(it))
                }
        }
    }
}