package com.mahmoud.altasherat.features.splash.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.features.language_country.domain.usecase.GetLanguageCodeUC
import com.mahmoud.altasherat.features.splash.domain.usecase.FetchCountriesUC
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
    private val fetchCountriesUC: FetchCountriesUC,
    private val getLanguageCodeUC: GetLanguageCodeUC
) : ViewModel() {

    private val _state = MutableStateFlow<SplashState>(SplashState.Idle)
    val state = _state.asStateFlow()

    private val _events = Channel<SplashEvent>()
    val events = _events.receiveAsFlow()

    private val _languageCode = MutableStateFlow<String?>(null)
    val languageCode: StateFlow<String?> = _languageCode


    init {
        fetchCountriesUC()
            .onEach { result ->
                Log.d("SplashResult", result.toString())
                _state.value = when (result) {
                    is Resource.Error -> {
                        _events.send(SplashEvent.Error(result.error))
                        SplashState.Error(result.error)
                    }

                    is Resource.Loading -> SplashState.Loading
                    is Resource.Success -> {
                        _events.send(SplashEvent.NavigateToHome)
                        SplashState.Success
                    }
                }
            }
            .launchIn(viewModelScope)


        getLanguageCode()

    }


    private fun getLanguageCode() {
        viewModelScope.launch {
            _languageCode.value = getLanguageCodeUC()
        }
    }
}