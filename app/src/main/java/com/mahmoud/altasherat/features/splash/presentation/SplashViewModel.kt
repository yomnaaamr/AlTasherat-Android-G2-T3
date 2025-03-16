package com.mahmoud.altasherat.features.splash.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.features.splash.domain.usecase.GetCountriesUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getCountriesUC: GetCountriesUC
) : ViewModel() {

    private val _state = MutableStateFlow<SplashState>(SplashState.Loading)
    val state = _state.asStateFlow()

    private val _events = Channel<SplashEvent>()
    val events = _events.receiveAsFlow()


    private val _isContentVisible = MutableStateFlow(false)
    val isContentVisible: StateFlow<Boolean> = _isContentVisible


    init {
        getCountriesUC()
            .onEach { result ->
                Log.d("ViewModel", "Current SplashState: $result") // Log the state
                _state.value = when (result) {
                    is Resource.Error -> {
//                        _events.send(SplashEvent.Error(result.error))
                        SplashState.Error(result.error)
                    }

                    is Resource.Loading -> SplashState.Loading
                    is Resource.Success -> {
//                        when i uncomment it the state not getting updated
//                        _events.send(SplashEvent.NavigateToHome)
//                        delay(5000)
                        SplashState.Success
//                        SplashState.Loading


                    }
                }
            }
            .launchIn(viewModelScope)
    }


    fun showContent() {
        _isContentVisible.value = true
    }
}