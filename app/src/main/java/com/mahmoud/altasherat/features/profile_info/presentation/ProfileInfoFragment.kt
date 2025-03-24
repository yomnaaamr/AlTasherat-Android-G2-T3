package com.mahmoud.altasherat.features.profile_info.presentation

import android.app.DatePickerDialog
import android.util.Log
import androidx.fragment.app.viewModels
import com.mahmoud.altasherat.common.presentation.CountryPickerBottomSheet
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.databinding.FragmentProfileInfoBinding
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.ListItem
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.models.User
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class ProfileInfoFragment :
    BaseFragment<FragmentProfileInfoBinding>(FragmentProfileInfoBinding::inflate) {

    private val viewModel: ProfileInfoViewModel by viewModels()
    private lateinit var bottomSheet: CountryPickerBottomSheet
    private var userCountry: Country? = null
    private var phoneCountry: Country? = null
    private var user: User? = null
    private var _countries: List<Country>? = null

    override fun FragmentProfileInfoBinding.initialize() {
        setupObservers()
        setupListeners()

    }

    private fun setupListeners() {
        binding.phoneCodePicker.setOnClickListener {
            val initialSelect = phoneCountry?.flag + " (" + phoneCountry?.phoneCode + ")"
            binding.phoneCodePicker.apply {
                setText(initialSelect)
                bottomSheet = CountryPickerBottomSheet(
                    _countries as List<ListItem>, phoneCountry!!.id.minus(1)
                ) { selectedCountry ->
                    phoneCountry = selectedCountry as Country
                    setText(phoneCountry?.flag + " (" + phoneCountry?.phoneCode + ")")
//                    viewModel.onAction(SignupContract.SignUpAction.UpdateCountryCode(selectedCountry.phoneCode))
//                    viewModel.onAction(SignupContract.SignUpAction.UpdateCountryID(selectedCountry.id.toString()))

                }
            }
            bottomSheet.show(childFragmentManager, "PhonePickerBottomSheet")
        }

        binding.birthdayEdit.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this.requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                    binding.birthdayEdit.setText(formattedDate)
                }, year, month, day
            )
            datePickerDialog.show()
        }

        binding.countryEdit.setOnClickListener {
            bottomSheet = CountryPickerBottomSheet(
                _countries as List<ListItem>, userCountry!!.id.minus(1)
            ) { selectedCountry ->
                userCountry = selectedCountry as Country
                binding.countryEdit.setText(userCountry?.flag + " " + userCountry?.name)
//                    viewModel.onAction(SignupContract.SignUpAction.UpdateCountryCode(selectedCountry.phoneCode))
//                    viewModel.onAction(SignupContract.SignUpAction.UpdateCountryID(selectedCountry.id.toString()))

            }
            bottomSheet.show(childFragmentManager, "CountryPickerBottomSheet")
        }
    }

    private fun setupObservers() {
        collectFlow(viewModel.state) { state ->
            when (state) {
                is ProfileInfoContract.ProfileInfoState.Error -> hideLoading()
                is ProfileInfoContract.ProfileInfoState.Idle -> showLoading()
                is ProfileInfoContract.ProfileInfoState.Loading -> showLoading()
                is ProfileInfoContract.ProfileInfoState.Success -> {
                    user = state.userResponse
                    fillFields(user)
                    hideLoading()
                }
            }
        }

        collectFlow(viewModel.countries) { countries ->
            if (countries.isEmpty()) return@collectFlow
            phoneCountry = countries.find { it.phoneCode == user?.phone?.countryCode }
            _countries = countries

        }
        collectFlow(viewModel.userCountry) { userCountry ->
            this.userCountry = userCountry
            Log.d("USER_COUNTRY", userCountry.toString())
            binding.countryEdit.setText(userCountry?.flag + " " + userCountry?.name)

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
            binding.countryEdit.setText("${userCountry?.flag} ${userCountry?.name}")

        }

    }


}