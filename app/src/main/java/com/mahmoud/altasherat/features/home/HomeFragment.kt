package com.mahmoud.altasherat.features.home

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
        }
    }
}