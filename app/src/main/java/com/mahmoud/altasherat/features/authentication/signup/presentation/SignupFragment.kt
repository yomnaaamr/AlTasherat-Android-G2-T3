package com.mahmoud.altasherat.features.authentication.signup.presentation

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.ValidationError
import com.mahmoud.altasherat.common.presentation.CountryPickerBottomSheet
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.common.presentation.base.delegators.MessageType
import com.mahmoud.altasherat.common.presentation.utils.toErrorMessage
import com.mahmoud.altasherat.databinding.FragmentSignupBinding
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.models.Country
import com.mahmoud.altasherat.features.authentication.AuthViewModel
import com.mahmoud.altasherat.features.authentication.login.presentation.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>(FragmentSignupBinding::inflate) {

    private lateinit var authViewModel: AuthViewModel
    private val viewModel: SignupViewModel by viewModels()
    private lateinit var bottomSheet: CountryPickerBottomSheet
    private var selectedCountry: Country? = null
    private var selectedPhoneCodePosition: Int = -1
    private var countries: List<Country> = emptyList()


    override fun FragmentSignupBinding.initialize() {

        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]

        setupObservers()
        setupListeners()
    }


    private fun setupObservers() {

        collectFlow(viewModel.state) { state ->
            when (state) {
                is SignupContract.SignUpState.Error -> hideLoading()
                is SignupContract.SignUpState.Idle -> {}
                is SignupContract.SignUpState.Loading -> showLoading()
                is SignupContract.SignUpState.Success -> hideLoading()
            }
        }


        collectFlow(viewModel.events) { signupEvent ->
            when (signupEvent) {
                is SignupContract.SignUpEvent.Error -> {
                    when (signupEvent.error) {
                        is AltasheratError.ValidationErrors -> {
                            displayValidationErrors(signupEvent.error.errors)
                        }

                        else -> {
                            val errorMessage =
                                signupEvent.error.toErrorMessage(requireContext())
                            showMessage(errorMessage, MessageType.SNACKBAR, this)
                        }
                    }
                }

                is SignupContract.SignUpEvent.NavigationToHome -> {
                    findNavController().navigate(R.id.action_authFragment_to_home_nav_graph)
                }


            }

        }


        collectFlow(viewModel.countries) { countries ->
            if (countries.isEmpty()) return@collectFlow
            val firstItem = countries.first()
            val initialSelect = resources.getString(
                R.string.country_picker_display,
                firstItem.flag,
                formatCountryCode(firstItem.phoneCode)
            )

            this.countries = countries
            selectedCountry = firstItem

            binding.phoneCodePicker.setText(initialSelect)
        }


    }


    private fun setupListeners() {
        binding.firstNameEdit.addTextChangedListener {
            viewModel.onAction(
                SignupContract.SignUpAction.UpdateFirstName(
                    it.toString()
                )
            )
        }
        binding.lastNameEdit.addTextChangedListener {
            viewModel.onAction(
                SignupContract.SignUpAction.UpdateLastName(
                    it.toString()
                )
            )
        }
        binding.emailEdit.addTextChangedListener {
            viewModel.onAction(
                SignupContract.SignUpAction.UpdateEmail(
                    it.toString()
                )
            )
        }
        binding.passwordEdit.addTextChangedListener {
            viewModel.onAction(
                SignupContract.SignUpAction.UpdatePassword(
                    it.toString()
                )
            )
        }
        binding.phoneEdit.addTextChangedListener {
            viewModel.onAction(
                SignupContract.SignUpAction.UpdatePhoneNumber(
                    it.toString()
                )
            )
        }

        binding.phoneCodePicker.setOnClickListener {
            val preSelectedPosition = if (selectedPhoneCodePosition != -1) {
                selectedPhoneCodePosition
            } else {
                countries.indexOfFirst { it.id == selectedCountry?.id }
            }
            bottomSheet = CountryPickerBottomSheet(
                countries ,
                preSelectedPosition,
                isPhonePicker = true
            ) { newSelectedCountry,position ->
                this@SignupFragment.selectedCountry = newSelectedCountry as Country
                selectedPhoneCodePosition = position
                binding.phoneCodePicker.setText(
                    resources.getString(
                        R.string.country_picker_display,
                        newSelectedCountry.flag,
                        formatCountryCode(newSelectedCountry.phoneCode)
                    ))


                viewModel.onAction(SignupContract.SignUpAction.UpdateCountryCode(newSelectedCountry.phoneCode))
                viewModel.onAction(SignupContract.SignUpAction.UpdateCountryID(newSelectedCountry.id.toString()))
            }

            bottomSheet.show(childFragmentManager, "CountryPickerBottomSheet")
        }

        binding.signupBtn.setOnClickListener {
            viewModel.onAction(SignupContract.SignUpAction.SignUp)
        }


        binding.signInTxt.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.auth_fragment_container, LoginFragment())
                .commit()

            authViewModel.switchToTab(0)

        }

    }


    private fun displayValidationErrors(errors: List<ValidationError>) {
        val errorFields = mapOf(
            setOf(
                ValidationError.EMPTY_FIRSTNAME,
                ValidationError.INVALID_FIRSTNAME
            ) to binding.firstNameEdit,
            setOf(
                ValidationError.EMPTY_LASTNAME,
                ValidationError.INVALID_LASTNAME
            ) to binding.lastNameEdit,
            setOf(ValidationError.EMPTY_EMAIL, ValidationError.INVALID_EMAIL) to binding.emailEdit,
            setOf(
                ValidationError.EMPTY_PASSWORD,
                ValidationError.INVALID_PASSWORD
            ) to binding.passwordLayout,
            setOf(
                ValidationError.EMPTY_PHONE_NUMBER,
                ValidationError.INVALID_PHONE_NUMBER
            ) to binding.phoneEdit,
            setOf(
                ValidationError.INVALID_COUNTRY_CODE,
                ValidationError.EMPTY_COUNTRY_CODE
            ) to binding.phoneCodePicker
        )

        binding.firstNameEdit.error = null
        binding.lastNameEdit.error = null
        binding.emailEdit.error = null
        binding.passwordLayout.isErrorEnabled = false
        binding.passwordLayout.error = null
        binding.phoneEdit.error = null
        binding.phoneCodePicker.error = null



        errors.forEach { error ->
            errorFields.entries.find { it.key.contains(error) }?.value?.let { view ->
                when (view) {
                    is TextInputLayout -> {
                        view.isErrorEnabled = true
                        view.error = error.toErrorMessage(requireContext())
                    }

                    is TextInputEditText -> {
                        view.error = error.toErrorMessage(requireContext())
                    }
                }
            }
        }


    }
}

