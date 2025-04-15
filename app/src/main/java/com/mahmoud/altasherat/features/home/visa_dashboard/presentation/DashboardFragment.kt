package com.mahmoud.altasherat.features.home.visa_dashboard.presentation

import androidx.navigation.fragment.findNavController
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.databinding.FragmentDashboardBinding


class DashboardFragment : BaseFragment<FragmentDashboardBinding>(FragmentDashboardBinding::inflate) {


    override fun FragmentDashboardBinding.initialize() {

        binding.homeLayoutOne.setOnClickListener {
//            not exist yet
//            findNavController().navigate(R.id.action_dashboardFragment_to_formScreen)
        }
    }

}