package com.mahmoud.altasherat.features.authentication.login.presentation

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.ValidationError
import com.mahmoud.altasherat.common.presentation.adapters.CountryPickerBottomSheet
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.common.presentation.base.delegators.MessageType
import com.mahmoud.altasherat.common.presentation.utils.toErrorMessage
import com.mahmoud.altasherat.databinding.FragmentLoginBinding
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.request.PhoneRequest
import com.mahmoud.altasherat.features.authentication.AuthViewModel
import com.mahmoud.altasherat.features.authentication.login.data.models.request.LoginRequest
import com.mahmoud.altasherat.features.authentication.signup.presentation.SignupFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private lateinit var authViewModel: AuthViewModel
    private val loginViewModel: LoginViewModel by viewModels()
    private var selectedCountry: Country? = null
    private lateinit var bottomSheet: CountryPickerBottomSheet



    override fun FragmentLoginBinding.initialize() {
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]

        setupObservers()
        setupListeners()
    }


    private fun setupListeners(){
        binding.createNewAccTxt.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.auth_fragment_container, SignupFragment())
                .commit()

            authViewModel.switchToTab(0)
        }


        binding.loginBtn?.setOnClickListener {
            val phoneNumber = binding.phoneEdit.text.toString()
            val password = binding.passwordEdit.text.toString()
            loginViewModel.onActionTrigger(
                LoginContract.LoginAction.LoginWithPhone(
                    LoginRequest(
                        phone = PhoneRequest(
                            countryCode = selectedCountry?.phoneCode ?: "00966", phoneNumber
                        ), password = password
                    )
                )
            )
        }

        binding.phoneCodePicker.setOnClickListener {
            bottomSheet.show(childFragmentManager, "CountryPickerBottomSheet")
        }
    }

    private fun setupObservers(){


        collectFlow(loginViewModel.countries) { countries ->
            if (countries.isEmpty()) return@collectFlow
            val firstItem = countries.first()
            val initialSelect = firstItem.flag + " (" + firstItem.phoneCode + ")"
            binding.phoneCodePicker.apply {
                setText(initialSelect)
                bottomSheet = CountryPickerBottomSheet(countries) { selectedCountry ->
                    this@LoginFragment.selectedCountry = selectedCountry as Country
                    setText(selectedCountry.flag + " (" + selectedCountry.phoneCode + ")")

                }
            }
        }

        collectFlow(loginViewModel.event){ event ->
            when (event) {
                is LoginContract.LoginEvent.NavigateToHome -> {
                    //Navigate to home
                    Log.d("AITASHERAT", "Navigate To Profile ")
                    findNavController().navigate(R.id.action_authFragment_to_homeFragment)
                    Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_LONG)
                        .show()
                }

                is LoginContract.LoginEvent.Error -> {
                    when (event.error) {
                        is AltasheratError.ValidationErrors -> {
                            displayValidationErrors(event.error.errors)
                        }

                        else -> {
                            val errorMessage = event.error.toErrorMessage(requireContext())
                            showMessage(errorMessage, MessageType.SNACKBAR, this)

                        }
                    }
                }
            }
        }


        collectFlow(loginViewModel.state){ state ->
            when (state) {
                is LoginContract.LoginState.Loading -> {
                    showLoading()
                }

                is LoginContract.LoginState.Success -> {
                    hideLoading()
                }

                is LoginContract.LoginState.Exception -> {
                    hideLoading()
                }

                is LoginContract.LoginState.Idle -> {

                }
            }
        }

    }



    private fun displayValidationErrors(errors: List<ValidationError>) {
        val errorFields = mapOf(
            setOf(
                ValidationError.EMPTY_PHONE_NUMBER,
                ValidationError.INVALID_PHONE_NUMBER
            ) to binding.phoneEdit,
            setOf(
                ValidationError.EMPTY_PASSWORD,
                ValidationError.INVALID_PASSWORD
            ) to binding.passwordEdit,
        )

        binding.phoneEdit.error = null
        binding.passwordEdit.error = null

        errors.forEach { error ->
            errorFields.entries.find { it.key.contains(error) }?.value?.error =
                error.toErrorMessage(requireContext())
        }
    }

}