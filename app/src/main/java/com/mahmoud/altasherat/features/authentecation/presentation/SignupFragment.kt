package com.mahmoud.altasherat.features.authentecation.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.mahmoud.altasherat.common.presentation.utils.toErrorMessage
import com.mahmoud.altasherat.databinding.FragmentSignupBinding
import com.mahmoud.altasherat.features.signup.presentation.SignUpAction
import com.mahmoud.altasherat.features.signup.presentation.SignUpEvent
import com.mahmoud.altasherat.features.signup.presentation.SignUpState
import com.mahmoud.altasherat.features.signup.presentation.SignupViewModel
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
            viewModel.onAction(SignUpAction.UpdateFirstName(it.toString()))
        }
        binding.lastNameEdit.addTextChangedListener{
            viewModel.onAction(SignUpAction.UpdateLastName(it.toString()))
        }
        binding.emailEdit.addTextChangedListener{
            viewModel.onAction(SignUpAction.UpdateEmail(it.toString()))
        }
        binding.passwordEdit.addTextChangedListener {
            viewModel.onAction(SignUpAction.UpdatePassword(it.toString()))
        }
        binding.phoneEdit.addTextChangedListener {
            viewModel.onAction(SignUpAction.UpdatePhoneNumber(it.toString()))
        }
        binding.signupBtn.setOnClickListener {
            viewModel.onAction(SignUpAction.SignUp)
        }

    }

}