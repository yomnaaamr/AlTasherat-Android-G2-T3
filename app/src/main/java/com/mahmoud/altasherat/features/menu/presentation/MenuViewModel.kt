package com.mahmoud.altasherat.features.menu.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.onError
import com.mahmoud.altasherat.common.domain.util.onSuccess
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.usecase.GetUserInfoUC
import com.mahmoud.altasherat.features.splash.domain.usecase.HasUserLoggedInUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val hasUserLoggedInUC: HasUserLoggedInUC,
    private val getUserInfoUC: GetUserInfoUC,
) : ViewModel() {


    private val _state = MutableStateFlow(MenuContract.MenuState())
    val state = _state.asStateFlow()

    private val _events = Channel<MenuContract.MenuEvent>()
    val events = _events.receiveAsFlow()


    init {


        viewModelScope.launch {
            hasUserLoggedInUC()
                .onSuccess { hasUser ->
                    _state.update {
                        it.copy(
                            isAuthenticated = hasUser,
                            screenState = MenuContract.MenuScreenState.Success
                        )
                    }
                }
                .onError { error ->
                    _events.send(MenuContract.MenuEvent.Error(error))
                    _state.update { it.copy(screenState = MenuContract.MenuScreenState.Error(error)) }
                }
        }

    }


    fun onAction(action: MenuContract.MenuAction) {
        when (action) {
            is MenuContract.MenuAction.GetUserData -> getUserData()
        }
    }


    private fun getUserData() {
        getUserInfoUC()
            .onEach { result ->
                _state.update {
                    when (result) {
                        is Resource.Loading -> it.copy(screenState = MenuContract.MenuScreenState.Loading)
                        is Resource.Success -> it.copy(
                            user = result.data,
                            screenState = MenuContract.MenuScreenState.Success
                        )

                        is Resource.Error -> {
                            _events.send(MenuContract.MenuEvent.Error(result.error))
                            it.copy(screenState = MenuContract.MenuScreenState.Error(result.error))
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }
}