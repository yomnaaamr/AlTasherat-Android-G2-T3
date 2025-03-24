package com.mahmoud.altasherat.features.profile_info.presentation

import android.util.Log
import androidx.fragment.app.viewModels
import com.mahmoud.altasherat.common.presentation.CountryPickerBottomSheet
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.databinding.FragmentProfileInfoBinding
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.models.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileInfoFragment :
    BaseFragment<FragmentProfileInfoBinding>(FragmentProfileInfoBinding::inflate) {

    private val viewModel: ProfileInfoViewModel by viewModels()
    private lateinit var bottomSheet: CountryPickerBottomSheet
    private var userCountry: Country? = null
    private var user: User? = null

    override fun FragmentProfileInfoBinding.initialize() {
        setupObservers()
    }

    private fun setupObservers() {
        collectFlow(viewModel.state) { state ->
            when (state) {
                is ProfileInfoContract.ProfileInfoState.Error -> hideLoading()
                is ProfileInfoContract.ProfileInfoState.Idle -> {}
                is ProfileInfoContract.ProfileInfoState.Loading -> showLoading()
                is ProfileInfoContract.ProfileInfoState.Success -> {
                    hideLoading()
                    user = state.userResponse
                    Log.d("User", user.toString())
                    fillFields(user)
                }
            }
        }

        collectFlow(viewModel.countries) { countries ->
            if (countries.isEmpty()) return@collectFlow
            userCountry = countries.let { it.find { it.phoneCode == user?.phone?.countryCode } }
            val initialSelect = userCountry?.flag + " (" + userCountry?.phoneCode + ")"
            binding.phoneCodePicker.apply {
                setText(initialSelect)
                bottomSheet = CountryPickerBottomSheet(countries) { selectedCountry ->
                    userCountry = selectedCountry as Country
                    setText(userCountry?.flag + " (" + userCountry?.phoneCode + ")")
//                    viewModel.onAction(SignupContract.SignUpAction.UpdateCountryCode(selectedCountry.phoneCode))
//                    viewModel.onAction(SignupContract.SignUpAction.UpdateCountryID(selectedCountry.id.toString()))
                }
            }
        }
    }

    private fun fillFields(user: User?) {
        if (user != null) {
            binding.firstNameEdit.setText(user.firstname)
            binding.middleNameEdit.apply {
                if (user.middleName.isNotEmpty()) {
                    setText(user.middleName)
                }
            }
            binding.lastNameEdit.setText(user.lastname)
            binding.phoneEdit.setText(user.phone.number)
            binding.phoneCodePicker.setText(userCountry?.flag + " (" + user.phone.countryCode + ")")
            binding.emailEdit.setText(user.email)
            binding.birthdayEdit.apply {
                if (user.birthDate.isNotEmpty()) {
                    setText(user.birthDate)
                }
            }
            binding.countryEdit.setText("${userCountry?.name} ${userCountry?.flag}")

        }

    }


}