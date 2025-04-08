package com.mahmoud.altasherat.features.home.menu.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.util.onEachErrorSuspend
import com.mahmoud.altasherat.common.domain.util.onEachSuccessSuspend
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.usecase.DeleteUserAccessTokenUC
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.usecase.GetUserInfoUC
import com.mahmoud.altasherat.features.splash.domain.usecase.HasUserLoggedInUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val hasUserLoggedInUC: HasUserLoggedInUC,
    private val getUserInfoUC: GetUserInfoUC,
    private val deleteTokenUC: DeleteUserAccessTokenUC,
    ) : ViewModel() {


    private val _state = MutableStateFlow(MenuContract.MenuState())
    val state = _state.asStateFlow()

    private val _events = Channel<MenuContract.MenuEvent>()
    val events = _events.receiveAsFlow()


    fun onAction(action: MenuContract.MenuAction) {
        when (action) {
            is MenuContract.MenuAction.GetUserData -> getUserData()
            is MenuContract.MenuAction.Logout -> logout()
        }
    }

    private fun logout() {
        deleteTokenUC()
            .onEachSuccessSuspend {
                _events.send(MenuContract.MenuEvent.NavigationToAuth)
            }
            .onEachErrorSuspend {
                _events.send(MenuContract.MenuEvent.Error(it))
            }
            .launchIn(viewModelScope)
    }


    init {

        hasUserLoggedInUC()
            .onEachSuccessSuspend { hasUser ->
                _state.update {
                    it.copy(
                        isAuthenticated = hasUser,
                        screenState = MenuContract.MenuScreenState.Success
                    )
                }
                if (hasUser) {
                    getUserData()
                }
            }
            .onEachErrorSuspend { error->
                _events.send(MenuContract.MenuEvent.Error(error))
                _state.update { it.copy(screenState = MenuContract.MenuScreenState.Error(error)) }
            }.launchIn(viewModelScope)

    }



    private fun getUserData() {
        getUserInfoUC()
            .onEachErrorSuspend { error ->
                _events.send(MenuContract.MenuEvent.Error(error))
                _state.update {
                    it.copy(screenState = MenuContract.MenuScreenState.Error(error))
                }
            }
            .onEachSuccessSuspend { result ->
                _state.update {
                    it.copy(
                        user = result,
                        screenState = MenuContract.MenuScreenState.Success
                    )
                }
            }.launchIn(viewModelScope)
    }
}