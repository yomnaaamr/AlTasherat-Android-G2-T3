package com.mahmoud.altasherat.features.menu.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.features.splash.domain.usecase.HasUserLoggedInUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val hasUserLoggedInUC: HasUserLoggedInUC
): ViewModel() {


    private val _state = MutableStateFlow<MenuContract.MenuState>(MenuContract.MenuState.Idle)
    val state = _state.asStateFlow()

    private val _events = Channel<MenuContract.MenuEvent>()
    val events = _events.receiveAsFlow()


    init {
        viewModelScope.launch {
            val isUserAuthenticated = hasUserLoggedInUC()
            _state.value = MenuContract.MenuState.Success(isUserAuthenticated)
        }
    }
}