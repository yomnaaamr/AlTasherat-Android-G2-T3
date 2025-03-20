package com.mahmoud.altasherat.features.splash.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.onError
import com.mahmoud.altasherat.common.domain.util.onSuccess
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase.GetLanguageCodeUC
import com.mahmoud.altasherat.features.onBoarding.domain.useCase.GetOnBoardingStateUC
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase.GetCountriesFromRemoteUC
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
    private val getOnBoardingStateUC: GetOnBoardingStateUC,
    private val hasUserLoggedInUC: HasUserLoggedInUC
) : ViewModel() {

    private val _state = MutableStateFlow<SplashState>(SplashState.Idle)
    val state = _state.asStateFlow()

    private val _events = Channel<SplashEvent>()
    val events = _events.receiveAsFlow()

    private val _languageCode = MutableStateFlow<String?>(null)
    val languageCode: StateFlow<String?> = _languageCode


    init {

        viewModelScope.launch {
            getOnBoardingStateUC()
                .onSuccess {
                    if (it) {
                        fetchCountries()
                    } else {
//                        check if user is logged in
                        if (hasUserLoggedInUC()) {
                            _events.send(SplashEvent.NavigateToHome)
                            _state.value = SplashState.Success
                        }else{
                            _events.send(SplashEvent.NavigateToAuth)
                            _state.value = SplashState.Success
                        }
                    }
                }
                .onError {
                    _events.send(SplashEvent.Error(it))
                    _state.value = SplashState.Error(it)
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
                        _events.send(SplashEvent.Error(result.error))
                        SplashState.Error(result.error)
                    }

                    is Resource.Loading -> SplashState.Loading
                    is Resource.Success -> {
                        _events.send(SplashEvent.NavigateToOnBoarding)
                        SplashState.Success
                    }
                }
            }
            .launchIn(viewModelScope)
        getLanguageCode()

//        viewModelScope.launch {
//            _state.value = when (val result = isFirstTimeToLaunchTheAppUC()) {
//                is Resource.Error -> {
//                    _events.send(SplashEvent.Error(result.error))
//                    SplashState.Error(result.error)
//                }
//
//                is Resource.Loading -> SplashState.Loading
//                is Resource.Success -> {
//                    Log.d("AITASHERAT", "if First time to launch? $result")
//                    if (result.data) {
//                        _events.send(SplashEvent.NavigateToOnBoarding)
//                    } else
//                        _events.send(SplashEvent.NavigateToHome)
//                    SplashState.Success
//
//                }
//            }
//        }

    }

    private fun getLanguageCode() {
        viewModelScope.launch {
            getLanguageCodeUC()
                .onSuccess { languageCode ->
                    _languageCode.value = languageCode
                }
                .onError {
                    _events.send(SplashEvent.Error(it))
                }
        }
    }
}