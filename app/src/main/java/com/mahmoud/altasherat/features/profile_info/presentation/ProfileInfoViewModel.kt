package com.mahmoud.altasherat.features.profile_info.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.onError
import com.mahmoud.altasherat.common.domain.util.onSuccess
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase.GetCountriesFromLocalUC
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.usecase.GetUserInfoUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileInfoViewModel @Inject constructor(
    private val getUserInfoUC: GetUserInfoUC,
    private val getCountriesFromLocalUC: GetCountriesFromLocalUC
) : ViewModel() {

    private val _state =
        MutableStateFlow<ProfileInfoContract.ProfileInfoState>(ProfileInfoContract.ProfileInfoState.Idle)
    val state = _state.asStateFlow()

    private val _events = Channel<ProfileInfoContract.ProfileInfoEvent>()
    val events = _events.receiveAsFlow()

    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries = _countries.asStateFlow()

    init {
        getUserData()
        getCountries()
    }

    private fun getCountries() {
        viewModelScope.launch {
            getCountriesFromLocalUC()
                .onSuccess { countries ->
                    _countries.value = countries
                }
                .onError {
                    _events.send(ProfileInfoContract.ProfileInfoEvent.Error(it))
                }
        }
    }

    private fun getUserData() {
        viewModelScope.launch {
            getUserInfoUC().onEach { result ->
                _state.value = when (result) {
                    is Resource.Loading -> ProfileInfoContract.ProfileInfoState.Loading
                    is Resource.Success -> ProfileInfoContract.ProfileInfoState.Success(result.data)
                    is Resource.Error -> {
                        _events.send(ProfileInfoContract.ProfileInfoEvent.Error(result.error))
                        ProfileInfoContract.ProfileInfoState.Error(result.error)
                    }

                }
            }
        }
    }

}