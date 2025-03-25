package com.mahmoud.altasherat.features.home

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun FragmentHomeBinding.initialize() {
        val navController =
            childFragmentManager.findFragmentById(R.id.home_fragment_container)?.findNavController()
        if (navController != null) {

            binding.bottomNav.setupWithNavController(navController)

            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.requestsFragment,R.id.dashboardFragment,
                    R.id.menuFragment -> {
                        binding.bottomNav.visibility = View.VISIBLE
                    }
                    else -> binding.bottomNav.visibility = View.GONE
                }
            }
        }


    }
}