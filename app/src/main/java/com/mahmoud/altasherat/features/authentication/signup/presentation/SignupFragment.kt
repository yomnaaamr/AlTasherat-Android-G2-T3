package com.mahmoud.altasherat.features.authentication.signup.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.presentation.utils.toErrorMessage
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Country
import com.mahmoud.altasherat.databinding.FragmentSignupBinding
import com.mahmoud.altasherat.features.authentication.AuthViewModel
import com.mahmoud.altasherat.features.authentication.login.presentation.LoginFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    private lateinit var authViewModel: AuthViewModel
    private val viewModel: SignupViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]

        //Use Original country list from local instead of this.
        val countryList = listOf(
            Country(1, "Saudi Arabia", "SAR", "sa", "00966", "🇸🇦"),
            Country(2, "Egypt", "EGP", "eg", "0020", "🇪🇬"),
            Country(3, "Afghanistan", "AFN", "af", "0093", "🇦🇫")
        )
        val countryDisplayList = countryList.map { "${it.flag} (${it.phoneCode})" }
        val adapter = ArrayAdapter(
            this.requireContext(),
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

        return binding.root
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { signupState ->
                        val message = when(signupState){
                            is SignUpState.Error -> "Error"
                            is SignUpState.Idle -> {}
                            is SignUpState.Loading -> "Loading"
                            is SignUpState.Success -> "Success"
                        }
                        Toast.makeText(requireContext(), message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.events.collect { signupEvent ->
                        when(signupEvent){
                            is SignUpEvent.Error -> {
                                val errorMessage = signupEvent.error.toErrorMessage(requireContext())
                                Toast.makeText(requireContext(),errorMessage, Toast.LENGTH_SHORT).show()
                            }
                            is SignUpEvent.NavigationToHome -> {
                                findNavController().navigate(R.id.action_authFragment_to_homeFragment)
                            }
                        }
                    }
                }
            }
        }
    }


    private fun setupListeners() {
        binding.firstNameEdit.addTextChangedListener{
            viewModel.onAction(com.mahmoud.altasherat.features.authentication.signup.presentation.SignUpAction.UpdateFirstName(it.toString()))
        }
        binding.lastNameEdit.addTextChangedListener{
            viewModel.onAction(com.mahmoud.altasherat.features.authentication.signup.presentation.SignUpAction.UpdateLastName(it.toString()))
        }
        binding.emailEdit.addTextChangedListener{
            viewModel.onAction(com.mahmoud.altasherat.features.authentication.signup.presentation.SignUpAction.UpdateEmail(it.toString()))
        }
        binding.passwordEdit.addTextChangedListener {
            viewModel.onAction(com.mahmoud.altasherat.features.authentication.signup.presentation.SignUpAction.UpdatePassword(it.toString()))
        }
        binding.phoneEdit.addTextChangedListener {
            viewModel.onAction(com.mahmoud.altasherat.features.authentication.signup.presentation.SignUpAction.UpdatePhoneNumber(it.toString()))
        }
        binding.signupBtn.setOnClickListener {
            viewModel.onAction(com.mahmoud.altasherat.features.authentication.signup.presentation.SignUpAction.SignUp)
        }

    }

}