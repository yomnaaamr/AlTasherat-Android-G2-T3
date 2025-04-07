package com.mahmoud.altasherat.features.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.util.onEachErrorSuspend
import com.mahmoud.altasherat.common.domain.util.onEachLoadingSuspend
import com.mahmoud.altasherat.common.domain.util.onEachSuccessSuspend
import com.mahmoud.altasherat.common.util.Constants
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.usecase.GetCountriesFromRemoteUC
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.usecase.HasCountriesUC
import com.mahmoud.altasherat.features.al_tashirat_services.language.domain.usecase.GetLanguageCodeUC
import com.mahmoud.altasherat.features.onBoarding.onboarding.domain.useCase.GetOnBoardingStateUC
import com.mahmoud.altasherat.features.splash.domain.usecase.HasUserLoggedInUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
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


        hasCountriesUC()
            .onEachSuccessSuspend { hasCountries ->
                if (hasCountries) {
                    isUserFirstTime()
                } else {
                    fetchCountries()
                }
            }
            .onEachErrorSuspend {
                _events.send(SplashContract.SplashEvent.Error(it))
                _state.value = SplashContract.SplashState.Error(it)
            }.launchIn(viewModelScope)


        getLanguageCode()

    }


    private fun isUserFirstTime() {

        getOnBoardingStateUC()
            .onEachSuccessSuspend { notFirstTime ->
                if (notFirstTime) {
                    hasLoggedInUser()
                } else {
                    _events.send(SplashContract.SplashEvent.NavigateToLanguage)
                    _state.value = SplashContract.SplashState.Success
                }
            }
            .onEachErrorSuspend {
                _events.send(SplashContract.SplashEvent.Error(it))
                _state.value = SplashContract.SplashState.Error(it)
            }
            .launchIn(viewModelScope)

    }


    private fun hasLoggedInUser() {

        hasUserLoggedInUC()
            .onEachSuccessSuspend { hasUser ->
                if (hasUser) {
                    _events.send(SplashContract.SplashEvent.NavigateToHome)
                } else {
                    _events.send(SplashContract.SplashEvent.NavigateToAuth)
                }
                _state.value = SplashContract.SplashState.Success
            }
            .onEachErrorSuspend {
                _events.send(SplashContract.SplashEvent.Error(it))
                _state.value = SplashContract.SplashState.Error(it)
            }.launchIn(viewModelScope)

    }


    private fun fetchCountries() {

        getCountriesFromRemoteUC(Constants.LOCALE_EN)
            .onEachSuccessSuspend {
                _events.send(SplashContract.SplashEvent.NavigateToLanguage)
                SplashContract.SplashState.Success
            }
            .onEachErrorSuspend {
                _events.send(SplashContract.SplashEvent.Error(it))
                _state.value = SplashContract.SplashState.Error(it)
            }
            .onEachLoadingSuspend {
                _state.value = SplashContract.SplashState.Loading
            }.launchIn(viewModelScope)

    }

    private fun getLanguageCode() {

        getLanguageCodeUC()
            .onEachSuccessSuspend { languageCode ->
                _languageCode.value = languageCode
            }
            .onEachErrorSuspend {
                _events.send(SplashContract.SplashEvent.Error(it))
            }.launchIn(viewModelScope)

    }
}