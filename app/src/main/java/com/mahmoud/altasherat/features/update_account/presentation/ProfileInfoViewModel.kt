package com.mahmoud.altasherat.features.update_account.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoud.altasherat.common.domain.util.onEachErrorSuspend
import com.mahmoud.altasherat.common.domain.util.onEachLoadingSuspend
import com.mahmoud.altasherat.common.domain.util.onEachSuccessSuspend
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.usecase.GetCountriesFromLocalUC
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.usecase.GetCountryUC
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.usecase.SaveSelectedCountryUC
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.usecase.GetUserInfoUC
import com.mahmoud.altasherat.features.update_account.data.models.request.UpdateAccRequest
import com.mahmoud.altasherat.features.update_account.domain.usecase.UpdateAccountUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileInfoViewModel @Inject constructor(
    private val getUserInfoUC: GetUserInfoUC,
    private val getCountriesFromLocalUC: GetCountriesFromLocalUC,
    private val getCountryUC: GetCountryUC,
    private val updateAccountUC: UpdateAccountUC,
    private val saveSelectedCountryUC: SaveSelectedCountryUC,

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
    val profileUiState = _profileUiState.asStateFlow()

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
                updateImage(
                    profileInfoAction.value
                )
            }

            is ProfileInfoContract.ProfileInfoAction.SaveChanges -> updateAccount()
            is ProfileInfoContract.ProfileInfoAction.UpdateCountry -> saveSelectedCountry(
                profileInfoAction.value
            )
        }
    }

    init {
        getCountries()
        getUserData()
        getUserCountry()

    }

    private fun updateAccount() {
        val updateAccRequest = UpdateAccRequest(
            firstName = _profileUiState.value.firstName,
            middlename = _profileUiState.value.middleName,
            lastname = _profileUiState.value.lastName,
            email = _profileUiState.value.email,
            birthDate = _profileUiState.value.birthdate,
            countryCode = _profileUiState.value.countryCode,
            number = _profileUiState.value.phoneNumber,
            image = _profileUiState.value.image,
            country = _profileUiState.value.selectedCountryId
        )

        updateAccountUC(updateAccRequest)
            .onEachSuccessSuspend {
                _events.send(ProfileInfoContract.ProfileInfoEvent.NavigationToProfileMenu)
                _state.value = ProfileInfoContract.ProfileInfoState.Success(it.user)
            }
            .onEachErrorSuspend {
                _events.send(ProfileInfoContract.ProfileInfoEvent.Error(it))
                _state.value = ProfileInfoContract.ProfileInfoState.Error(it)
            }
            .onEachLoadingSuspend {
                _state.value = ProfileInfoContract.ProfileInfoState.Loading
            }.launchIn(viewModelScope)

    }

    private fun getCountries() {

        getCountriesFromLocalUC()
            .onEachSuccessSuspend { countries ->
                _countries.value = countries
            }
            .onEachErrorSuspend {
                _events.send(ProfileInfoContract.ProfileInfoEvent.Error(it))
            }
            .launchIn(viewModelScope)


    }

    private fun getUserData() {

        getUserInfoUC()
            .onEachSuccessSuspend { result ->
                _state.value = ProfileInfoContract.ProfileInfoState.Success(result)
            }
            .onEachErrorSuspend {
                _events.send(ProfileInfoContract.ProfileInfoEvent.Error(it))
                _state.value = ProfileInfoContract.ProfileInfoState.Error(it)
            }
            .launchIn(viewModelScope)
    }

    private fun getUserCountry() {

        getCountryUC()
            .onEachSuccessSuspend { result ->
                _userCountry.value = result
            }
            .onEachErrorSuspend {
                _events.send(ProfileInfoContract.ProfileInfoEvent.Error(it))
            }.launchIn(viewModelScope)
    }

    private fun saveSelectedCountry(value: Country) {

        saveSelectedCountryUC(value)
            .onEachSuccessSuspend {  }
            .onEachErrorSuspend {
                _events.send(ProfileInfoContract.ProfileInfoEvent.Error(it))
            }.launchIn(viewModelScope)
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
        _profileUiState.update { it.copy(image = value) }
    }

    private fun updateCountryID(countryId: String) {
        _profileUiState.update { it.copy(selectedCountryId = countryId) }
    }


}