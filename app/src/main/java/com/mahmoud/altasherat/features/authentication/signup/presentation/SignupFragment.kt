package com.mahmoud.altasherat.features.authentication.signup.presentation

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.ValidationError
import com.mahmoud.altasherat.common.presentation.CountryPickerBottomSheet
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.common.presentation.base.delegators.MessageType
import com.mahmoud.altasherat.common.presentation.utils.toErrorMessage
import com.mahmoud.altasherat.databinding.FragmentSignupBinding
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Country
import com.mahmoud.altasherat.features.authentication.AuthViewModel
import com.mahmoud.altasherat.features.authentication.login.presentation.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>(FragmentSignupBinding::inflate) {

    private lateinit var authViewModel: AuthViewModel
    private val viewModel: SignupViewModel by viewModels()
    private lateinit var bottomSheet: CountryPickerBottomSheet


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
            val initialSelect = firstItem.flag + " (" + firstItem.phoneCode + ")"
            binding.phoneCodePicker.apply {
                setText(initialSelect)
                bottomSheet = CountryPickerBottomSheet(countries) { selectedCountry ->
                    val country = selectedCountry as Country
                    setText(country.flag + " (" + country.phoneCode + ")")
                    viewModel.onAction(SignupContract.SignUpAction.UpdateCountryCode(selectedCountry.phoneCode))
                    viewModel.onAction(SignupContract.SignUpAction.UpdateCountryID(selectedCountry.id.toString()))
                }
            }
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
            bottomSheet.show(childFragmentManager, "CountryPickerBottomSheet")
        }

        binding.signupBtn.setOnClickListener {
            viewModel.onAction(SignupContract.SignUpAction.SignUp)
        }


        binding.signInTxt.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.auth_fragment_container, LoginFragment())
                .commit()

            authViewModel.switchToTab(1)

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
            ) to binding.passwordEdit,
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
        binding.passwordEdit.error = null
        binding.phoneEdit.error = null
        binding.phoneCodePicker.error = null

        errors.forEach { error ->
            errorFields.entries.find { it.key.contains(error) }?.value?.error =
                error.toErrorMessage(requireContext())
        }


    }
}

