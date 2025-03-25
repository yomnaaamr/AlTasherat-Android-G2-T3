package com.mahmoud.altasherat.features.profile_info.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.onError
import com.mahmoud.altasherat.common.domain.util.onSuccess
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase.GetCountriesFromLocalUC
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.usecase.GetCountryUC
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.request.PhoneRequest
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.usecase.GetUserInfoUC
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.usecase.UpdateUserInfoUC
import com.mahmoud.altasherat.features.profile_info.data.models.request.UpdateAccRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileInfoViewModel @Inject constructor(
    private val getUserInfoUC: GetUserInfoUC,
    private val getCountriesFromLocalUC: GetCountriesFromLocalUC,
    private val getCountryUC: GetCountryUC,
    private val updateUserInfoUC: UpdateUserInfoUC
) : ViewModel() {

    private val _state =
        MutableStateFlow<ProfileInfoContract.ProfileInfoState>(ProfileInfoContract.ProfileInfoState.Idle)
    val state = _state.asStateFlow()

    private val _events = Channel<ProfileInfoContract.ProfileInfoEvent>()
    val events = _events.receiveAsFlow()

    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries = _countries.asStateFlow()

    private val _userCountry = MutableStateFlow<Country?>(null)
    val userCountry = _userCountry.asStateFlow()

    private val _profileUiState = MutableStateFlow(ProfileInfoUiState())
    private val profileUiSate = _profileUiState.asStateFlow()

    fun onAction(profileInfoAction: ProfileInfoContract.ProfileInfoAction) {
        when (profileInfoAction) {
            is ProfileInfoContract.ProfileInfoAction.UpdateFirstName -> updateFirstName(
                profileInfoAction.value
            )

            is ProfileInfoContract.ProfileInfoAction.UpdateMiddleName -> updateMiddleName(
                profileInfoAction.value
            )

            is ProfileInfoContract.ProfileInfoAction.UpdateLastName -> updateLastName(
                profileInfoAction.value
            )

            is ProfileInfoContract.ProfileInfoAction.UpdateEmail -> updateEmail(profileInfoAction.value)
            is ProfileInfoContract.ProfileInfoAction.UpdateBirthday -> updateBirthDate(
                profileInfoAction.value
            )

            is ProfileInfoContract.ProfileInfoAction.UpdatePhoneNumber -> updatePhoneNumber(
                profileInfoAction.phone
            )

            is ProfileInfoContract.ProfileInfoAction.UpdateCountryCode -> updateCountryCode(
                profileInfoAction.countryCode
            )

            is ProfileInfoContract.ProfileInfoAction.UpdateCountryID -> updateCountryID(
                profileInfoAction.countryId
            )

            is ProfileInfoContract.ProfileInfoAction.UpdateImage -> {
                Log.e("UPDATE_IMAGE_ACTION", "called")
                updateImage(
                    profileInfoAction.value
                )
            }

            is ProfileInfoContract.ProfileInfoAction.SaveChanges -> updateAccount()

        }
    }

    init {
        getCountries()
        getUserData()
        getUserCountry()

    }

    private fun updateAccount() {
        val phone = PhoneRequest(
            countryCode = profileUiSate.value.countryCode,
            number = profileUiSate.value.phoneNumber,
        )
        val updateAccRequest = UpdateAccRequest(
            firstName = profileUiSate.value.firstName,
            middlename = profileUiSate.value.middleName,
            lastname = profileUiSate.value.lastName,
            email = profileUiSate.value.email,
            birthDate = profileUiSate.value.birthdate,
            phone = phone,
            image = profileUiSate.value.image,
            country = profileUiSate.value.selectedCountryId
        )
        Log.d("UPDATE_REQUEST", updateAccRequest.toString())

        updateUserInfoUC(updateAccRequest)
            .onEach { result ->
                Log.d("usecaseResult", result.toString())
                _state.value = when (result) {
                    is Resource.Error -> {
                        _events.send(ProfileInfoContract.ProfileInfoEvent.Error(result.error))
                        ProfileInfoContract.ProfileInfoState.Error(result.error)
                    }

                    is Resource.Loading -> ProfileInfoContract.ProfileInfoState.Loading
                    is Resource.Success -> {
                        Log.d("SUCCESS_RESULT", result.data.user.toString())
                        _events.send(ProfileInfoContract.ProfileInfoEvent.NavigationToProfileMenu)
                        ProfileInfoContract.ProfileInfoState.Success(result.data.user)
                    }
                }
            }.launchIn(viewModelScope)

    }

    private fun getCountries() {
        viewModelScope.launch {
            getCountriesFromLocalUC().onSuccess { countries ->
                _countries.value = countries
            }.onError {
                _events.send(ProfileInfoContract.ProfileInfoEvent.Error(it))
            }
        }
    }

    private fun getUserData() {
        viewModelScope.launch {
            getUserInfoUC().collect { result ->
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

    private fun getUserCountry() {
        viewModelScope.launch {
            getCountryUC().onSuccess { result ->
                _userCountry.value = result
            }.onError {
                _events.send(ProfileInfoContract.ProfileInfoEvent.Error(it))
            }
        }
    }

    private fun updateFirstName(value: String) {
        _profileUiState.update { it.copy(firstName = value) }
    }

    private fun updateMiddleName(value: String) {
        _profileUiState.update { it.copy(middleName = value) }
    }

    private fun updateLastName(value: String) {
        _profileUiState.update { it.copy(lastName = value) }
    }

    private fun updateEmail(value: String) {
        _profileUiState.update { it.copy(email = value) }
    }

    private fun updateBirthDate(value: String) {
        _profileUiState.update { it.copy(birthdate = value) }
    }

    private fun updatePhoneNumber(value: String) {
        _profileUiState.update { it.copy(phoneNumber = value) }
    }

    private fun updateCountryCode(countryCode: String) {
        _profileUiState.update { it.copy(countryCode = countryCode) }
    }

    private fun updateImage(value: File?) {
        Log.d("IMAGE_FILE", value.toString())
        _profileUiState.update { it.copy(image = value) }
    }

    private fun updateCountryID(countryId: String) {
        _profileUiState.update { it.copy(selectedCountryId = countryId) }
    }


}