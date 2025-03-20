package com.mahmoud.altasherat.features.splash.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.presentation.utils.changeLocale
import com.mahmoud.altasherat.common.presentation.utils.toErrorMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        setupObservers()
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }


    private fun setupObservers() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    splashViewModel.state.collect { splashState ->
                        Log.d("AITASHERAAT", "state = $splashState")
                        when (splashState) {
                            is SplashState.Idle -> {}
                            is SplashState.Loading -> {}
                            is SplashState.Success -> {
                                // Handle Success state
                            }

                            is SplashState.Error -> {
                                // Handle Error state
                            }
                        }
                    }

                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    splashViewModel.events.collect { splashEvent ->
                        when (splashEvent) {
                            is SplashEvent.NavigateToHome -> {
                                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                            }

                            is SplashEvent.Error -> {
                                val errorMessage =
                                    splashEvent.error.toErrorMessage(requireContext())
                                showToast(errorMessage)
                            }

                            SplashEvent.NavigateToOnBoarding -> {
                                findNavController().navigate(R.id.action_splashFragment_to_onBoardingFragment2)
                            }

                            SplashEvent.NavigateToAuth -> {
                                findNavController().navigate(R.id.action_splashFragment_to_authFragment)

                            }
                        }
                    }

                }


            }
        }



        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    splashViewModel.languageCode.collect { languageCode ->
                        if (languageCode != null) {
                            requireContext().changeLocale(languageCode)
                        } else {
                            //  Set a default locale
                            requireContext().changeLocale( "en")
                        }
                    }
                }
            }
        }

    }

    private fun showToast(
        message: String,
        duration: Int = Toast.LENGTH_LONG,
    ) {
        Toast.makeText(requireContext(), message, duration).show()
    }
}