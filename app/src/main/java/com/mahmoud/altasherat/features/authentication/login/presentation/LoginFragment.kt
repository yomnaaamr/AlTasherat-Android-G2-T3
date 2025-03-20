package com.mahmoud.altasherat.features.authentication.login.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.presentation.utils.toErrorMessage
import com.mahmoud.altasherat.databinding.FragmentLoginBinding
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.request.PhoneRequest
import com.mahmoud.altasherat.features.authentication.AuthViewModel
import com.mahmoud.altasherat.features.authentication.login.data.models.request.LoginRequest
import com.mahmoud.altasherat.features.authentication.signup.presentation.SignupFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var authViewModel: AuthViewModel
    private val loginViewModel: LoginViewModel by viewModels()
    private var selectedCountry: Country? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)

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
                selectedCountry = countryList[position]  // Get the selected country object

                setText(selectedItem, false)
            }

        }
        binding.createNewAccTxt.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.auth_fragment_container, SignupFragment())
                .commit()

            authViewModel.switchToTab(0)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.event.collect() { event ->
                    when (event) {
                        is LoginContract.LoginEvent.NavigateToHome -> {
                            //Navigate to home
                            Log.d("AITASHERAT", "Navigate To Profile ")
                            findNavController().navigate(R.id.action_authFragment_to_homeFragment)
                            Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.state.collect { state ->
                    when (state) {
                        is LoginContract.LoginState.Loading -> {
                            //Show loading
                        }

                        is LoginContract.LoginState.Success -> {
                            //Navigate to home
                        }

                        is LoginContract.LoginState.Exception -> {
                            //Show error
                            handleError(state.altasheratError)
                        }

                        LoginContract.LoginState.Idle -> {

                        }
                    }
                }
            }
        }
    }

    private fun handleError(altasheratError: AltasheratError) {
        val errorMessages: String = altasheratError.toErrorMessage(requireContext())
        Log.d("AITASHERAT", "errormsgs = $errorMessages")

    }


}