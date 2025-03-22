package com.mahmoud.altasherat.features.splash.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.onError
import com.mahmoud.altasherat.common.domain.util.onSuccess
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase.GetCountriesFromLocalUC
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase.GetCountriesFromRemoteUC
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase.GetLanguageCodeUC
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
    private val getCountriesFromLocalUC: GetCountriesFromLocalUC
) : ViewModel() {

    private val _state = MutableStateFlow<SplashContract.SplashState>(SplashContract.SplashState.Idle)
    val state = _state.asStateFlow()

    private val _events = Channel<SplashContract.SplashEvent>()
    val events = _events.receiveAsFlow()

    private val _languageCode = MutableStateFlow<String?>(null)
    val languageCode: StateFlow<String?> = _languageCode


    init {

        viewModelScope.launch {
            getCountriesFromLocalUC()
                .onSuccess { countriesList->
                    val hasCountries = countriesList.isNotEmpty()
                    if (!hasCountries) {
                        fetchCountries()
                    } else {
                        val hasUser = hasUserLoggedInUC()
                        if (hasUser) {
                            _events.send(SplashContract.SplashEvent.NavigateToHome)
                            _state.value = SplashContract.SplashState.Success
                        } else {
                            _events.send(SplashContract.SplashEvent.NavigateToAuth)
                            _state.value = SplashContract.SplashState.Success
                        }
                    }
                }
                .onError {
                    _events.send(SplashContract.SplashEvent.Error(it))
                    _state.value = SplashContract.SplashState.Error(it)
                }
        }


        getLanguageCode()

    }


    private fun fetchCountries() {

        getCountriesFromRemoteUC()
            .onEach { result ->
                Log.d("SplashResult", result.toString())
                _state.value = when (result) {
                    is Resource.Error -> {
                        _events.send(SplashContract.SplashEvent.Error(result.error))
                        SplashContract.SplashState.Error(result.error)
                    }

                    is Resource.Loading -> SplashContract.SplashState.Loading
                    is Resource.Success -> {
                        _events.send(SplashContract.SplashEvent.NavigateToOnBoarding)
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