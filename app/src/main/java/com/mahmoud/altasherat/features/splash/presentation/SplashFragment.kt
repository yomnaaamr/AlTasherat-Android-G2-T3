package com.mahmoud.altasherat.features.splash.presentation

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.common.presentation.base.delegators.MessageType
import com.mahmoud.altasherat.common.presentation.utils.changeLocale
import com.mahmoud.altasherat.common.presentation.utils.toErrorMessage
import com.mahmoud.altasherat.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val splashViewModel: SplashViewModel by viewModels()


    override fun FragmentSplashBinding.initialize() {
        setupObservers()
    }


    private fun setupObservers() {

        collectFlow(splashViewModel.state) { splashState ->
            when (splashState) {
                is SplashContract.SplashState.Idle -> {}
                is SplashContract.SplashState.Loading -> {}
                is SplashContract.SplashState.Success -> {
                    // Handle Success state
                }

                is SplashContract.SplashState.Error -> {
                    // Handle Error state
                }
            }
        }


        collectFlow(splashViewModel.events) { splashEvent ->
            when (splashEvent) {
                is SplashContract.SplashEvent.NavigateToHome -> {
                    findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                }

                is SplashContract.SplashEvent.Error -> {
                    val errorMessage =
                        splashEvent.error.toErrorMessage(requireContext())
                    showMessage(errorMessage, MessageType.SNACKBAR, this)
                }

                SplashContract.SplashEvent.NavigateToOnBoarding -> {
                    findNavController().navigate(R.id.action_splashFragment_to_onBoardingFragment2)
                }

                SplashContract.SplashEvent.NavigateToAuth -> {
                    findNavController().navigate(R.id.action_splashFragment_to_authFragment)

                }
            }

        }



        collectFlow(splashViewModel.languageCode) { languageCode ->
            if (languageCode != null) {
                requireContext().changeLocale(languageCode)
            } else {
                //  Set a default locale
                requireContext().changeLocale("en")
            }

        }

    }

}