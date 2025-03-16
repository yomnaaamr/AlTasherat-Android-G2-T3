package com.mahmoud.altasherat.features.splash.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.features.splash.domain.usecase.GetCountriesUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getCountriesUC: GetCountriesUC,
) : ViewModel() {

    private val _state = MutableStateFlow<SplashState>(SplashState.Idle)
    val state = _state.asStateFlow()

    private val _events = Channel<SplashEvent>()
    val events = _events.receiveAsFlow()


    init {
        viewModelScope.launch {
            Log.d("AITASHERAAT", "viewModel")
            getCountriesUC()
                .onEach { result ->
                    Log.d("AITASHERAAT", "result = $result")
                    _state.value = when (result) {
                        is Resource.Error -> {
                            _events.send(SplashEvent.Error(result.error))
                            SplashState.Error(result.error)
                        }

                        is Resource.Loading -> SplashState.Loading
                        is Resource.Success -> {
                            Log.d("AITASHERAAT", "Succes Splash")
                            _events.send(SplashEvent.NavigateToHome)
                            SplashState.Success
                        }
                    }
                }

        }
    }
}