package com.mahmoud.altasherat.features.authentication.signup.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.ValidationError
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.common.presentation.base.delegators.MessageType
import com.mahmoud.altasherat.common.presentation.utils.toErrorMessage
import com.mahmoud.altasherat.databinding.FragmentSignupBinding
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Country
import com.mahmoud.altasherat.features.authentication.AuthViewModel
import com.mahmoud.altasherat.features.authentication.login.presentation.LoginFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>(FragmentSignupBinding::inflate){

    private lateinit var authViewModel: AuthViewModel
    private val viewModel: SignupViewModel by viewModels()


    override fun FragmentSignupBinding.initialize() {

        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]

        //Use Original country list from local instead of this.
        val countryList = listOf(
            Country(1, "Saudi Arabia", "SAR", "sa", "00966", "🇸🇦"),
            Country(2, "Egypt", "EGP", "eg", "0020", "🇪🇬"),
            Country(3, "Afghanistan", "AFN", "af", "0093", "🇦🇫")
        )
        val countryDisplayList = countryList.map { "${it.flag} (${it.phoneCode})" }
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            countryDisplayList
        )
        binding.phoneCodePicker.apply {
            setText(countryDisplayList[0], false)
            setOnClickListener {
                setAdapter(adapter)
                showDropDown()
            }
            //Handle item click
            setOnItemClickListener { parent, _, position, _ ->

                val selectedItem = parent.getItemAtPosition(position).toString()
                val selectedCountry = countryList[position]  // Get the selected country object

                setText(selectedItem, false)
            }

        }

        binding.signInTxt.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.auth_fragment_container, LoginFragment())
                .commit()

            authViewModel.switchToTab(1)

        }


        setupObservers()
        setupListeners()
    }



    private fun setupObservers() {

        collectFlow(viewModel.state){ state ->
            when(state) {
                is SignUpState.Error -> hideLoading()
                is SignUpState.Idle -> {}
                is SignUpState.Loading -> showLoading()
                is SignUpState.Success -> hideLoading()
            }
        }


        collectFlow(viewModel.events){signupEvent->
            when (signupEvent) {
                is SignUpEvent.Error -> {
                    when (signupEvent.error) {
                        is AltasheratError.ValidationErrors -> {
                            displayValidationErrors(signupEvent.error.errors)
                        }

                        else -> {
                            val errorMessage =
                                signupEvent.error.toErrorMessage(requireContext())
                            showMessage(errorMessage,MessageType.SNACKBAR,this)
                        }
                    }
                }

                is SignUpEvent.NavigationToHome -> {
                    findNavController().navigate(R.id.action_authFragment_to_homeFragment)
                }


            }

        }

    }


    private fun setupListeners() {
        binding.firstNameEdit.addTextChangedListener {
            viewModel.onAction(
                SignUpAction.UpdateFirstName(
                    it.toString()
                )
            )
        }
        binding.lastNameEdit.addTextChangedListener {
            viewModel.onAction(
                SignUpAction.UpdateLastName(
                    it.toString()
                )
            )
        }
        binding.emailEdit.addTextChangedListener {
            viewModel.onAction(
                SignUpAction.UpdateEmail(
                    it.toString()
                )
            )
        }
        binding.passwordEdit.addTextChangedListener {
            viewModel.onAction(
                SignUpAction.UpdatePassword(
                    it.toString()
                )
            )
        }
        binding.phoneEdit.addTextChangedListener {
            viewModel.onAction(
                SignUpAction.UpdatePhoneNumber(
                    it.toString()
                )
            )
        }
        binding.signupBtn.setOnClickListener {
            viewModel.onAction(SignUpAction.SignUp)
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

